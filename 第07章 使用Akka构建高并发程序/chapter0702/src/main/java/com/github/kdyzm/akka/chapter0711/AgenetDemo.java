package com.github.kdyzm.akka.chapter0711;

import akka.actor.*;
import akka.agent.Agent;
import akka.dispatch.ExecutionContexts;
import akka.dispatch.Futures;
import akka.dispatch.OnComplete;
import com.typesafe.config.ConfigFactory;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

/**
 * 有待研究是怎么回事儿
 */
public class AgenetDemo {

    public static Agent<Integer> counterAgent = Agent.create(0, ExecutionContexts.global());

    static ConcurrentLinkedQueue<Future<Integer>> futures = new ConcurrentLinkedQueue<Future<Integer>>();

    public static void main(String[] args) {
        final ActorSystem system = ActorSystem.create("main", ConfigFactory.load("samplehello.conf"));

        ActorRef[] counter = new ActorRef[10];

        for (int i = 0; i < counter.length; i++) {
            counter[i] = system.actorOf(Props.create(CounterActor.class), "counter_" + i);
        }
        final Inbox inbox = Inbox.create(system);
        for (int i = 0; i < counter.length; i++) {
            inbox.send(counter[i], 1);//这里发送任何整数都可以，只是起到信号的作用而已
            inbox.watch(counter[i]);
        }

        int closeCount = 0;

        //等待所有actor全部结束
        while (true) {
            Object msg=inbox.receive(Duration.create(1, TimeUnit.SECONDS));
            if(msg instanceof Terminated){
                closeCount++;
                if(closeCount == counter.length){
                    break;
                }
            }else{
                System.out.println(msg);
            }
        }

        //等待所有的累加线程完成，因为它们都是异步的
        Futures.sequence(futures,system.dispatcher()).onComplete(new OnComplete<Iterable<Integer>>() {
            @Override
            public void onComplete(Throwable throwable, Iterable<Integer> integers) throws Throwable {
                System.out.println("counterAgent="+counterAgent.get());
                system.shutdown();
            }
        },system.dispatcher());

    }
}
