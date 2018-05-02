package com.github.kdyzm.akka.chapter0713;

import akka.actor.ActorSelection;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * 管理和通知全局最优
 */
public class MasterBird extends UntypedActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    private PsoValue gBest = null;

    @Override
    public void onReceive(Object msg) throws Exception {
        if (msg instanceof PBestMsg) {
            PsoValue pBest = ((PBestMsg) msg).getValue();
            if (gBest == null || gBest.value < pBest.value) {
                //更新全局最优，通知所有粒子
                System.out.printf(msg+"\n");
                gBest = pBest;
                ActorSelection selection = getContext().actorSelection("/user/bird_*");
                selection.tell(new GBestMsg(gBest), getSelf());
            }
        } else {
            System.out.println("MasterBird:"+"无法处理的消息"+msg);
            unhandled(msg);
        }
    }
}
