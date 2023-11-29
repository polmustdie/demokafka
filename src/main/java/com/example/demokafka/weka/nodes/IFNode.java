package com.example.demokafka.weka.nodes;

import weka.core.Instance;


public class IFNode extends WekaNode {

    private double score = 0.0d; // cluster index


    public IFNode(Instance instance) {
        super(instance);
    }

    public void setScore(double s) {
        this.score = s;
    }

    public double getScore() {
        return this.score;
    }
}
