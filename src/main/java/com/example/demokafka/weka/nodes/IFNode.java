package com.example.demokafka.weka.nodes;

import lombok.Getter;
import lombok.Setter;
import weka.core.Instance;

import java.text.ParseException;

@Setter
@Getter
public class IFNode extends WekaNode {
    private String timestamp;

    private double score = 0.0d; // cluster index


    public IFNode(Instance instance) throws ParseException {
        super(instance);
    }
}
