package com.github.kdyzm.akka.chapter0713;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 该类表示一个可行的解
 * 主要包含两个信息：
 * 1. 投资规划的方案，即每一年分别需要投资多少钱
 * 2. 这个投资方案的总收益
 * <p>
 * x[1]、x[2]、x[3]、x[4]分别代表第一年、第二年、第三年和第四年的投资额，这里为方便起见，忽略了x[0]
 */
public final class PsoValue {
    final double value;
    final List<Double> x;

    public PsoValue(double v, List<Double> x) {
        value = v;
        List<Double> b = new ArrayList<>();
        b.addAll(x);
        this.x = Collections.unmodifiableList(b);
    }

    public double getValue() {
        return value;
    }

    public List<Double> getX() {
        return x;
    }

    @Override
    public String toString() {
        return "PsoValue{" +
                "value=" + value +
                ", x=" + x +
                '}';
    }
}
