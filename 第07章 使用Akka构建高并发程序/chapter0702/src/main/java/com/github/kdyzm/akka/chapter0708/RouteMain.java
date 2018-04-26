package com.github.kdyzm.akka.chapter0708;

import com.github.kdyzm.akka.chapter0704.MyWorker;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.agent.Agent;
import akka.dispatch.ExecutionContexts;

public class RouteMain {
	public static Agent<Boolean> flag=Agent.create(true, ExecutionContexts.global());
	
	private static final ActorSystem system = ActorSystem.create("router", ConfigFactory.load("samplehello.conf"));
	
	public static void main(String[] args) throws InterruptedException {
		ActorRef w = system.actorOf(Props.create(WatchActor.class), "watcher");
		int i=1;
		while(flag.get()){
			w.tell(MyWorker.Msg.WORKING, ActorRef.noSender());
			if(i%10 == 0){
				w.tell(MyWorker.Msg.CLOSE, ActorRef.noSender());
			}
			i++;
			Thread.sleep(100);
		}
		
	}
}
