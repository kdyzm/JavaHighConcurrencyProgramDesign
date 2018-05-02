package com.github.kdyzm.akka.chapter0710;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class MyPrinter extends UntypedActor{

    private final LoggingAdapter log= Logging.getLogger(getContext().system(),this );

    @Override
    public void onReceive(Object msg) throws Exception {

        if(msg instanceof Integer){
            log.info("PrintWriter:"+msg);
        }else{
            log.info("PrintWriter:"+"不支持的数据类型");
            unhandled(msg);
        }

    }
}
