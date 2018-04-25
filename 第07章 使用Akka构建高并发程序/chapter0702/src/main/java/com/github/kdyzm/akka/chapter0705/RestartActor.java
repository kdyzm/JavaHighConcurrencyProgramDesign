package com.github.kdyzm.akka.chapter0705;

import akka.actor.UntypedActor;
import scala.Option;

public class RestartActor extends UntypedActor {

	public enum Msg {
		DONE, RESTART
	}

	@Override
	public void preStart() throws Exception {
		System.out.println("pre start hashcode=" + this.hashCode());
	}

	@Override
	public void preRestart(Throwable reason, Option<Object> message) throws Exception {
		System.out.println("pre reStart hashcode=" + this.hashCode());
	}

	@Override
	public void postStop() throws Exception {
		System.out.println("post stop hashcode=" + this.hashCode());
	}

	public void postRestart(Throwable reason) throws Exception {
		super.postRestart(reason);
		System.out.println("post restart hashcode=" + this.hashCode());
	};

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg == Msg.DONE) {
			getContext().stop(getSelf());
		} else if (msg == Msg.RESTART) {
			// 1.模拟空指针情况
			 System.out.println(((Object) null).toString());
			// 抛出异常，默认会被restart，但是这里会resume
			// 2.模拟除以零的情况
//			double a = 0 / 0;
		}
		unhandled(msg);
	}

}
