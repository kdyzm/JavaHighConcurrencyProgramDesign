package com.github.kdyzm.akka.chapter0709;

import akka.actor.*;
import com.github.kdyzm.akka.chapter0708.WatchActor;
import com.typesafe.config.ConfigFactory;

public class ProducerMain {
    private static final ActorSystem system = ActorSystem.create("router", ConfigFactory.load("samplehello.conf"));
    public static void main(String args[]){
        ActorRef child=system.actorOf(Props.create(BabyActor.class),"child");
//        system.actorOf(Props.create(WatchActor.class,child), "watcher");
        child.tell(BabyActor.Msg.PLAY,ActorRef.noSender());
        child.tell(BabyActor.Msg.SLEEP,ActorRef.noSender());
        child.tell(BabyActor.Msg.PLAY,ActorRef.noSender());
        child.tell(BabyActor.Msg.PLAY,ActorRef.noSender());

        child.tell(PoisonPill.getInstance(), ActorRef.noSender());
    }
}
