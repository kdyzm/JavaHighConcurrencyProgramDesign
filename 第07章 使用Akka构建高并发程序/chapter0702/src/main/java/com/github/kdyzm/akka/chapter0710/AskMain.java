package com.github.kdyzm.akka.chapter0710;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.PoisonPill;
import akka.actor.Props;
import akka.pattern.Patterns;

import com.typesafe.config.ConfigFactory;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

public class AskMain {
    private static final ActorSystem system = ActorSystem.create("askdemo", ConfigFactory.load("samplehello.conf"));
    public static void main(String args[]) throws Exception {
        ActorRef worker=system.actorOf(Props.create(MyWorker.class),"workor");
        ActorRef printer=system.actorOf(Props.create(MyPrinter.class),"printer");

        //第一种方式：同步等待future返回
        Future<Object> f = Patterns.ask(worker, 5, 1000);
        int  result = (int)Await.result(f, Duration.create(6, TimeUnit.SECONDS));
        System.out.println("return:"+result);

        //第二种方式：直接导向其它actor，pipe不会等待
        f = Patterns.ask(worker,6,1000 );
        Patterns.pipe(f,system.dispatcher()).to(printer);
        worker.tell(PoisonPill.getInstance(),ActorRef.noSender());
    }
}
