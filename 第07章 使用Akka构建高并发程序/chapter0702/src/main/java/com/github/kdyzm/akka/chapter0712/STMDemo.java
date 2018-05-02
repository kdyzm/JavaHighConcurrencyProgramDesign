package com.github.kdyzm.akka.chapter0712;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.transactor.Coordinated;
import akka.util.Timeout;
import com.typesafe.config.ConfigFactory;
import scala.concurrent.Await;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

public class STMDemo {

    public static ActorRef compay = null;
    public static ActorRef employee = null;

    public static void main(String[] args) throws Exception {
        final ActorSystem system = ActorSystem.create("main", ConfigFactory.load("samplehello.conf"));
        employee = system.actorOf(Props.create(EmployeeActor.class), "employee");
        compay = system.actorOf(Props.create(CompanyActor.class), "compayny");
        Timeout timeout = new Timeout(1, TimeUnit.SECONDS);
        for (int i = 0; i < 20; i++) {
            compay.tell(new Coordinated(i, timeout), ActorRef.noSender());
            Thread.sleep(200);
            Integer companyCount = (Integer) Await.result(Patterns.ask(compay, "GetCount", timeout), (Duration) timeout.duration());
            Integer employeeCount = (Integer) Await.result(Patterns.ask(employee, "GetCount", timeout), (Duration) timeout.duration());

            System.out.println("company count=" + companyCount);
            System.out.println("employee count=" + employeeCount);
            System.out.println("=======================");
        }
    }
}
