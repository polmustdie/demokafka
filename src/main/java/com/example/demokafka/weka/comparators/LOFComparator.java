package com.example.demokafka.weka.comparators;

//import org.example.nodes.LOFNode;

import com.example.demokafka.weka.nodes.LOFNode;

import java.util.Comparator;

public class LOFComparator implements Comparator<LOFNode> {

    public int compare(LOFNode o1, LOFNode o2) {
        if (o1.getLOF() > o2.getLOF()) {
            return -1;
        } else if (o1.getLOF() < o2.getLOF()) {
            return 1;
        } else {
            return 0;
        }
    }
}
