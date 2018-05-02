package com.github.kdyzm.akka.chapter0712;

import akka.actor.UntypedActor;
import akka.transactor.Coordinated;
import scala.concurrent.stm.Ref;
import scala.concurrent.stm.japi.STM;

public class EmployeeActor extends UntypedActor {

    private Ref.View<Integer> count = STM.newRef(50);//员工余额初始为50

    @Override
    public void onReceive(Object msg) throws Exception {
        if (msg instanceof Coordinated) {
            final Coordinated c = (Coordinated) msg;
            int downcount = (Integer) c.getMessage();
            try{
                c.atomic(() -> {
                    STM.increment(count, downcount);
                });
            }catch (Exception ex){
                ex.printStackTrace();
            }
        } else if ("GetCount".equals(msg)) {
            getSender().tell(count.get(), getSelf());
        } else {
            unhandled(msg);
        }
    }
}
