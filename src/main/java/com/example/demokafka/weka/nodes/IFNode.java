package com.example.demokafka.weka.nodes;

import weka.core.Instance;

import java.text.ParseException;


public class IFNode extends WekaNode {
    private String timestamp;

    private double score = 0.0d; // cluster index


    public IFNode(Instance instance) throws ParseException {
        super(instance);
    }

    public void setScore(double s) {
        this.score = s;
    }

}
