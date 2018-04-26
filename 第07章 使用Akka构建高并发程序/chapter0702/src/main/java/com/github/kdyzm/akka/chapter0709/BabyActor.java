package com.github.kdyzm.akka.chapter0709;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Procedure;

public class BabyActor extends UntypedActor{

    private final LoggingAdapter log= Logging.getLogger(getContext().system(),this );

    public static enum Msg{
        SLEEP,PLAY,CLOSE
    }

    /**
     * 这里的泛型必须使用Object？不使用就会报错
     */
    Procedure<Object> angry=new Procedure<Object>() {
        @Override
        public void apply(Object msg) throws Exception {
            log.info("angry apply:"+msg);
            if(msg == Msg.SLEEP){
                getSender().tell("i'm already angry",getSelf());
                log.info("i'm already angry ..");
            }
            if(msg == Msg.PLAY){
                log.info("i like playing");
                getContext().become(happy);
            }
        }
    };

    Procedure<Object> happy=new Procedure<Object>() {
        @Override
        public void apply(Object msg) throws Exception {
            log.info("happy apply:"+msg);
            if(msg == Msg.SLEEP){
                log.info("i dont want to sleep  ..");
                getContext().become(angry);
            }
            if(msg == Msg.PLAY){
                log.info("i'm already happy .");
                getSender().tell("i'm already happy",getSelf());
            }
        }
    };

    @Override
    public void onReceive(Object msg) throws Exception {
        log.info("on receive msg:"+msg);
        if(msg == Msg.SLEEP){
            getContext().become(happy);
        }else if(msg == Msg.PLAY){
            getContext().become(angry);
        }else{
            unhandled(msg);
        }
    }
}
