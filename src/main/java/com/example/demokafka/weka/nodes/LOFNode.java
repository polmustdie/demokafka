package com.example.demokafka.weka.nodes;

import lombok.Getter;
import lombok.Setter;
import weka.core.Instance;

import java.text.ParseException;

// is used to simulate the characteristic of each instance

@Setter
@Getter
public class LOFNode extends WekaNode {

    private double lof = 0.0d; // weight value


     //To initialize the instance with features and class label

    public LOFNode(Instance instance) throws ParseException {
        super(instance);
    }

//    public void setLOF(double lof) {
//        this.lof = lof;
//    }
//
//    public double getLOF() {
//        return this.lof;
//    }
}
