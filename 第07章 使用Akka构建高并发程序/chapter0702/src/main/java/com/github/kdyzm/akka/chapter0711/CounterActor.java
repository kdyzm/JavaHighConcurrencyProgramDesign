package com.github.kdyzm.akka.chapter0711;

import akka.actor.UntypedActor;
import akka.dispatch.Mapper;
import scala.concurrent.Future;

public class CounterActor extends UntypedActor {

    Mapper addmapper = new Mapper<Integer, Integer>() {
        @Override
        public Integer apply(Integer i) {
            return i + 1;
        }
    };

    @Override
    public void onReceive(Object msg) throws Exception {
        if (msg instanceof Integer) {
            for (int i = 0; i < 10000; i++) {
                Future<Integer> f = AgenetDemo.counterAgent.alter(addmapper);
                AgenetDemo.futures.add(f);
            }
            getContext().stop(getSelf());
        }else{
            unhandled(msg);
        }
    }


}