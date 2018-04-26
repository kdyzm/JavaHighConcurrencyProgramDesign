package com.github.kdyzm.akka.chapter0708;

import java.util.ArrayList;
import java.util.List;

import com.github.kdyzm.akka.chapter0704.MyWorker;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.Terminated;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.routing.ActorRefRoutee;
import akka.routing.RoundRobinRoutingLogic;
import akka.routing.Routee;
import akka.routing.Router;

public class WatchActor extends UntypedActor {

	private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

	public Router router;

	{
		List<Routee> routees = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			ActorRef worker = getContext().actorOf(Props.create(MyWorker.class), "worker_" + i);
			getContext().watch(worker);
			routees.add(new ActorRefRoutee(worker));
		}
		router = new Router(new RoundRobinRoutingLogic(), routees);
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof MyWorker.Msg) {
			router.route(msg, getSender());
		} else if (msg instanceof Terminated) {
			router = router.removeRoutee(((Terminated) msg).actor());
			//size 方法在eclipse 中报错，换成intelij ide报错消失
			log.info(((Terminated) msg).actor().path() + " is closed,routees=" + router.routees().size());
			if (router.routees().size() == 0) {
				log.info("close system");
				RouteMain.flag.send(false);
				getContext().system().shutdown();
			}
		} else {
			unhandled(msg);
		}
	}

}
