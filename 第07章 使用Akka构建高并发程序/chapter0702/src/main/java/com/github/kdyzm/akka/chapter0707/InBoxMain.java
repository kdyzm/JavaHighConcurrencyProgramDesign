package com.github.kdyzm.akka.chapter0707;

import java.util.concurrent.TimeUnit;

import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Inbox;
import akka.actor.Props;
import akka.actor.Terminated;
import scala.concurrent.duration.Duration;

public class InBoxMain {

	private static final ActorSystem system = ActorSystem.create("inboxdemo", ConfigFactory.load("samplehello.conf"));

	public static void main(String[] args) {
		ActorRef worker = system.actorOf(Props.create(MyWorker.class), "worker");
		final Inbox inbox = Inbox.create(system);
		inbox.watch(worker);
		inbox.send(worker, MyWorker.Msg.WORKING);
		inbox.send(worker, MyWorker.Msg.DONE);
		inbox.send(worker, MyWorker.Msg.CLOSE);

		while (true) {
			Object msg = inbox.receive(Duration.create(1, TimeUnit.SECONDS));
			if (msg == MyWorker.Msg.CLOSE) {
				System.out.println("my worker is closing");
			} else if (msg instanceof Terminated) {
				System.out.println("my worker is dead");
				system.shutdown();
				break;
			} else {
				System.out.println(msg);
			}

		}

	}
}
