package com.github.kdyzm.akka.chapter0713;

import akka.actor.ActorSelection;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 定义了成员变量
 */
public class Bird extends UntypedActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    private PsoValue pBest = null;//个体最优
    private PsoValue gBest = null;//全局最优
    private List<Double> velocity = new ArrayList<>(5);//当前粒子在各个维度上的速度
    private List<Double> x = new ArrayList<>(5);//投资方案，即每一年的投资额
    private Random r = new Random();//粒子群算法中需要随机数

    @Override
    public void preStart() throws Exception {
        //分别初始化速度和投资方案为无穷小
        for (int i = 0; i < 5; i++) {
            velocity.add(Double.NEGATIVE_INFINITY);
            x.add(Double.NEGATIVE_INFINITY);
        }

        //x1<=400
        x.set(1, (double) r.nextInt(401));

        //x2<=440-x1*.1
        double max = 440 - 1.1 * x.get(1);
        if (max < 0) max = 0;
        x.set(2, r.nextDouble() * max);

        //x3 <= 484-1.21*x1-1.1*x2
        max = 484 - 1.21 * x.get(1) - 1.1 * x.get(2);
        if (max <= 0) max = 0;
        x.set(3, r.nextDouble() * max);

        //x4 <= 532.4-1.331*x1-1.21*x2-1.1*x3
        max = 532.4 - 1.331 * x.get(1) - 1.21 * x.get(2) - 1.1 * x.get(3);
        if (max <= 0) max = 0;
        x.set(4, r.nextDouble() * max);

        //计算适应度（收益）
        double newFit = Fitness.fitness(x);
        pBest = new PsoValue(newFit, x);

        PBestMsg pBestMsg = new PBestMsg(pBest);

        //将计算结果发送给master
//        System.out.println(getSelf().path()+" 发送新pBest="+pBestMsg);
        ActorSelection selection = getContext().actorSelection("/user/masterbird");
        selection.tell(pBestMsg, getSelf());
    }

    /**
     * master计算出当前全局最优之后，会将全局最优发送给每一个粒子，粒子根据<br/>
     * 全局最优更新自己的运行速度，并更新自己的速度以及当前位置
     */
    @Override
    public void onReceive(Object msg) throws Exception {
        if (msg instanceof GBestMsg) {
            gBest = ((GBestMsg) msg).getValue();
            //更新速度
            for (int i = 1; i < velocity.size(); i++) {
                updateVelocity(i);
            }
            //更新位置
            for (int i = 1; i < x.size(); i++) {
                updateX(i);
            }

            //检查当前位置是否已经超出了合法范围
            validateX();

            //如果产生了新的个体最优适应度，则将其发送给master
            double newFit=Fitness.fitness(x);
            if(newFit>pBest.value){
                pBest=new PsoValue(newFit,x);
                PBestMsg pBestMsg=new PBestMsg(pBest);
                getSender().tell(pBestMsg,getSelf());
            }
        } else {
            System.out.println("Bird:无法处理的消息"+msg);
            unhandled(msg);
        }
    }

    //更新速度
    public double updateVelocity(int i) {
        double v = Math.random() * velocity.get(i)
                + 2 * Math.random() * (pBest.getX().get(i) - x.get(i))
                + 2 * Math.random() * (gBest.getX().get(i) - x.get(i));
        v = v > 0 ? Math.min(v, 5) : Math.max(v, -5);
        velocity.set(i, v);
        return v;
    }

    //更新位置(先更新粒子速度，再更新粒子位置)
    public double updateX(int i) {
        double newX = x.get(i) + velocity.get(i);
        x.set(i, newX);
        return newX;
    }

    /**
     * 每一年的投资都是由限额的，因此，要避免粒子跑到合理空间之外<br/>
     * 该方法强制将粒子约束在合理的区间中
     */
    public void validateX() {

        //校验x1
        if(x.get(1)>400){
            x.set(1, (double) r.nextInt(401));
        }

        //校验x2
        double max=440-1.1*x.get(1);
        if(x.get(2)>max || x.get(2)<0){
            x.set(2,r.nextDouble()*max);
        }

        //校验x3
        max=484-1.21*x.get(1)-1.1*x.get(2);
        if(x.get(3)>max || x.get(3)<0){
            x.set(3,r.nextDouble()*max);
        }

        //校验x4
        max=532.4-1.331*x.get(1)-1.21*x.get(2)-1.1*x.get(3);
        if(x.get(4)>max || x.get(4)<0){
            x.set(4,r.nextDouble()*max);
        }
    }


}
