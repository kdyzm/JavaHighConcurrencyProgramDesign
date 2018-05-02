package com.github.kdyzm.akka.chapter0712;

import akka.actor.UntypedActor;
import akka.transactor.Coordinated;
import scala.concurrent.stm.Ref;
import scala.concurrent.stm.japi.STM;

public class CompanyActor extends UntypedActor {

    private Ref.View<Integer> count = STM.newRef(100);

    @Override
    public void onReceive(Object msg) throws Exception {
        if (msg instanceof Coordinated) {
            final Coordinated c = (Coordinated) msg;
            final int downCount = (int) c.getMessage();
            STMDemo.employee.tell(c.coordinate(downCount), getSelf());//通知雇员账户增加余额中的金额
            c.atomic(() -> {
                if (count.get() < downCount) {//公司账户余额不足
                    throw new RuntimeException("company=" + count.get() + " less than employee=" + downCount);
                }
                STM.increment(count, -downCount);//公司账户余额减去已经付款的金额
            });
        } else if ("GetCount".equals(msg)) {
            getSender().tell(count.get(), getSelf());
        } else {
            unhandled(msg);
        }

    }
}
