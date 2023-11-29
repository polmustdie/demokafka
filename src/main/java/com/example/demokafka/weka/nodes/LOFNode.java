package com.example.demokafka.weka.nodes;

import weka.core.Instance;

/***
 * <p>This class <b>LOFNode</b> is used to simulate the characteristic of each instance.</p>
 *
 */
public class LOFNode extends WekaNode {

    private double lof = 0.0d; // weight value

    /**
     * To initialize the instance with features and class label
     */
    public LOFNode(Instance instance) {
        super(instance);
    }


    public void setLOF(double lof) {
        this.lof = lof;
    }

    public double getLOF() {
        return this.lof;
    }
}
