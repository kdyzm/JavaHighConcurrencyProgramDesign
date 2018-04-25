package com.github.kdyzm.akka.chapter0705;

import java.util.concurrent.TimeUnit;

import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.actor.SupervisorStrategy.Directive;
import akka.actor.UntypedActor;
import akka.japi.Function;
import scala.concurrent.duration.Duration;

public class SuperVisor extends UntypedActor {

	private static SupervisorStrategy strategy = new OneForOneStrategy(3, Duration.create(1, TimeUnit.MINUTES),
			new Function<Throwable, Directive>() {
				public Directive apply(Throwable t) {
					if (t instanceof ArithmeticException) {
						System.out.println("meet ArithmeticException ,just resume.");
						return SupervisorStrategy.resume();// 不做处理
					} else if (t instanceof NullPointerException) {
						System.out.println("meet NullPointException , restart.");
						return SupervisorStrategy.restart();// 重启actor
					} else if (t instanceof IllegalArgumentException) {
						return SupervisorStrategy.stop();// 停止actor
					} else {
						return SupervisorStrategy.escalate();// 抛出去，由更顶层的actor处理
					}
				};
			});

	@Override
	public SupervisorStrategy supervisorStrategy() {
		return strategy;
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof Props) {
			getContext().actorOf((Props) msg, "restartActor");
		} else {
			unhandled(msg);
		}
	}

}
