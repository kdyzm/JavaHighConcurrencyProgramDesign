package com.github.kdyzm.akka.chapter0705;

import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class StrategyMain {
	public static void customStrategy(ActorSystem system) {
		ActorRef a = system.actorOf(Props.create(SuperVisor.class), "Supervisor");
		a.tell(Props.create(RestartActor.class), ActorRef.noSender());
		//这里使用这种方式的原因是更加方便看到层级关系
		ActorSelection sel = system.actorSelection("akka://lifecycle/user/Supervisor/restartActor");
		for (int i = 0; i < 100; i++) {
			sel.tell(RestartActor.Msg.RESTART, ActorRef.noSender());
		}
	}

	public static void main(String[] args) {
		ActorSystem system = ActorSystem.create("lifecycle", ConfigFactory.load("samplehello.conf"));
		customStrategy(system);

	}
}
