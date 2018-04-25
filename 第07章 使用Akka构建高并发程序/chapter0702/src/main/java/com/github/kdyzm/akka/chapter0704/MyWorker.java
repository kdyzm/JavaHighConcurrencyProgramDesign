package com.github.kdyzm.akka.chapter0704;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import scala.Option;

public class MyWorker extends UntypedActor {

	private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

	public static enum Msg {
		WORKING, DONE, CLOSE
	}

	@Override
	public void preRestart(Throwable reason, Option<Object> message) throws Exception {
		System.out.println("my worker is starting");
	}

	@Override
	public void postStop() throws Exception {
		System.out.println("my worker is stopping");
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg == Msg.WORKING) {
			System.out.println("i am working,sender="+getSender().path());
		}
		if (msg == Msg.DONE) {
			System.out.println("stop working,sender="+getSender().path());
		}
		if (msg == Msg.CLOSE) {
			System.out.println("i will shutdown,sender="+getSender().path());
			getSender().tell(Msg.CLOSE, getSelf());
			getContext().stop(getSelf());
		}else{
			unhandled(msg);
		}
	}

}
