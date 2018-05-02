package com.github.kdyzm.akka.chapter0713;

public class PBestMsg {
    final PsoValue value;

    public PBestMsg(PsoValue value) {
        this.value = value;
    }

    public PsoValue getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "PBestMsg{" +
                "value=" + value +
                '}';
    }
}
