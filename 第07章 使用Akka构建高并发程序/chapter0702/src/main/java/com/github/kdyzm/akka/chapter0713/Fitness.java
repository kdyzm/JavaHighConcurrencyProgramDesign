package com.github.kdyzm.akka.chapter0713;

import java.util.List;

/**
 * 计算每年投资额x和最终受益value的算法如下
 */
public class Fitness {
    public static double fitness(List<Double> x) {
        double sum = 0;
        for (int i = 1; i < x.size(); i++) {
            sum += Math.sqrt(x.get(i));
        }
        return sum;
    }
}
