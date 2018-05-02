package com.github.kdyzm.akka.chapter0713;

import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.ConfigFactory;

/**
 * 启动类
 */
public class PSOMain {
    public static final int BIRD_COUNT = 1000000;

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("psoSystem", ConfigFactory.load("samplehello.conf"));
        system.actorOf(Props.create(MasterBird.class), "masterbird");
        for (int i = 0; i < BIRD_COUNT; i++) {
            system.actorOf(Props.create(Bird.class), "bird_" + i);
        }
    }
}
