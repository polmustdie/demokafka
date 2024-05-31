package com.example.demokafka.weka.nodes;

import lombok.Getter;
import lombok.Setter;
import weka.core.Instance;

import java.text.ParseException;

@Setter
@Getter
public class SBSNode extends WekaNode {

    private double clusterIndex = -1.0d; // cluster index

    public SBSNode(Instance instance) throws ParseException {
        super(instance);
    }
}
