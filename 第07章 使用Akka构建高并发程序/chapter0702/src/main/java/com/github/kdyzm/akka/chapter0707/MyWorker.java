package com.github.kdyzm.akka.chapter0707;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class MyWorker extends UntypedActor {

	private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

	public static enum Msg {
		WORKING, DONE, CLOSE
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg == Msg.WORKING) {
			log.info("i'm working");
		}
		if (msg == Msg.CLOSE) {
			log.info("i will shutdown");
			getSender().tell(Msg.CLOSE, getSelf());
			getContext().stop(getSelf());
		} else {
			unhandled(msg);
		}

	}

}
