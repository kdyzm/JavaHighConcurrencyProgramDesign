package com.github.kdyzm.akka.chapter0710;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class MyWorker extends UntypedActor{

    private final LoggingAdapter log= Logging.getLogger(getContext().system(),this );

    @Override
    public void onReceive(Object msg) throws Exception {
        if(msg instanceof Integer){
            int i= (int) msg;
            try{
                Thread.sleep(1000);
            }catch(Exception e){
            }
            getSender().tell(i*i,getSelf());
        }else{
            log.error("MyWorker 只接受整数类型的消息");
        }
    }
}
