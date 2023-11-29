package com.example.demokafka.weka.nodes;

import weka.core.Instance;


public class GAUNode extends WekaNode {

    private double probability = 0.0d; // weight value


    public GAUNode(Instance instance) {
        super(instance);
    }


    public void setProbability(double p) {
        this.probability = p;
    }

    public double getProbability() {
        return this.probability;
    }
}
