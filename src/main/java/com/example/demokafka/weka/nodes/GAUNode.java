package com.example.demokafka.weka.nodes;

import weka.core.Instance;

import java.text.ParseException;


public class GAUNode extends WekaNode {
    private double probability = 0.0d; // weight value

    public GAUNode(Instance instance) throws ParseException {
        super(instance);
    }

    public void setProbability(double p) {
        this.probability = p;
    }

    public double getProbability() {
        return this.probability;
    }
}
