package com.example.demokafka.weka.nodes;

import weka.core.Instance;

public class SBSNode extends WekaNode {

    private double clusterIndex = -1.0d; // cluster index

    public SBSNode(Instance instance) {
        super(instance);
    }

    public void setClusterIndex(double clusterIndex) {
        this.clusterIndex = clusterIndex;
    }

    public double getClusterIndex() {
        return this.clusterIndex;
    }
}
