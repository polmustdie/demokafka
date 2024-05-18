package com.example.demokafka.weka.nodes;

import lombok.Getter;
import lombok.Setter;
import weka.core.Instance;

import java.text.ParseException;

@Setter
@Getter
public class GAUNode extends WekaNode {
    private double probability = 0.0d; // weight value

    public GAUNode(Instance instance) throws ParseException {
        super(instance);
    }
}
