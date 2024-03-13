package com.example.demokafka.weka.nodes;

import weka.core.Instance;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
	private Date date;
	private Integer user_id;
	private Integer point_id;

	private String label; // class label

	private String prelabel = "normal"; // outlier or normal

	private List<Double> lsAttr = new ArrayList<>(); // feature list
	public WekaNode(Instance instance) throws ParseException {
		int lenAttr = instance.numAttributes();
		label = instance.stringValue(lenAttr - 1); // set true label
		String[] dateArray = instance.toString().split(",");
		date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.parse(dateArray[0].substring(1,dateArray[0].length()-1));
		for (int i = 0; i < lenAttr - 1; i++) { // set feature-values
			lsAttr.add(instance.value(i));
		}
	}
	public boolean isOutlier() {
		return prelabel.equals("outlier");
	}
	public Date getDate(){
		return this.date;
	}

}
