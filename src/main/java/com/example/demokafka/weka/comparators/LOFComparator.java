package com.example.demokafka.weka.comparators;


import com.example.demokafka.weka.nodes.LOFNode;

import java.util.Comparator;

public class LOFComparator implements Comparator<LOFNode> {

    public int compare(LOFNode o1, LOFNode o2) {
        if (o1.getLof() > o2.getLof()) {
            return -1;
        } else if (o1.getLof() < o2.getLof()) {
            return 1;
        } else {
            return 0;
        }
    }
}
