package com.example.demokafka.weka.nodes;

import weka.core.Instance;

import java.util.ArrayList;
import java.util.List;

public class WekaNode {

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getPrelabel() {
		return prelabel;
	}

	public void setPrelabel(String prelabel) {
		this.prelabel = prelabel;
	}

	public List<Double> getLsAttr() {
		return lsAttr;
	}

	public void setLsAttr(List<Double> lsAttr) {
		this.lsAttr = lsAttr;
	}

	private String label; // class label

	private String prelabel = "normal"; // outlier or normal

	private List<Double> lsAttr = new ArrayList<>(); // feature list
	public WekaNode(Instance instance) {
		int lenAttr = instance.numAttributes();
		label = instance.stringValue(lenAttr - 1); // set true label
		for (int i = 0; i < lenAttr - 1; i++) { // set feature-values
			lsAttr.add(instance.value(i));
		}
	}
	public boolean isOutlier() {
		return prelabel.equals("outlier");
	}

}
