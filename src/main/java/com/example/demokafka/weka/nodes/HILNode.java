package com.example.demokafka.weka.nodes;

import com.example.demokafka.weka.HilOut;
import com.example.demokafka.weka.utils.DistanceCalculator;
import lombok.Getter;
import weka.core.Instance;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Getter
public class HILNode extends WekaNode {

    private final List<HILNode> KNeighbors = new ArrayList<>(); // k-nearest neighbors

    private double weight = 0.0d; // weight value


    public HILNode(Instance instance) throws ParseException {
        super(instance);
    }


    public double getDistanceToOther(HILNode node) {
        double distance;
        List<Double> attr1 = getLsAttr();
        List<Double> attr1Cropped = new ArrayList<>();
        attr1Cropped.add(attr1.get(3));
        attr1Cropped.add(attr1.get(4));


        List<Double> attr2 = node.getLsAttr();
        List<Double> attr2Cropped = new ArrayList<>();
        attr2Cropped.add(attr2.get(3));
        attr2Cropped.add(attr2.get(4));


        distance = DistanceCalculator.distanceEculidean(attr1Cropped, attr2Cropped); //List<Double> ls1, List<Double> ls2

        return distance;
    }

    public void setNeighbor(HILNode node) {
        if (KNeighbors.size() < HilOut.k_test)
            KNeighbors.add(node);
    }

    public void setWeight() {
        for (HILNode nodes : KNeighbors) {
            weight += getDistanceToOther(nodes);
        }
    }

//    public double getWeight() {
//        return weight;
//    }
}
