package com.example.demokafka.weka.nodes;

import lombok.Getter;
import lombok.Setter;
import weka.core.Instance;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Getter
@Setter
public class WekaNode {
	private Date date;
	private Integer userId;
	private Integer pointId;

	private String label; // class label

	private String prelabel = "normal"; // outlier or normal

	private List<Double> lsAttr = new ArrayList<>(); // feature list

	public WekaNode(Instance instance) throws ParseException {
		int lenAttr = instance.numAttributes();
		label = instance.stringValue(lenAttr - 1); // set true label
		String[] dateArray = instance.toString().split(",");
		date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.parse(dateArray[0].substring(1,dateArray[0].length()-1));
		pointId = Integer.valueOf(dateArray[1]);
		userId= Integer.valueOf(dateArray[2]);
		for (int i = 0; i < lenAttr - 1; i++) { // set feature-values
//			if (i != 1 && i != 2) {
			lsAttr.add(instance.value(i));
//			}
		}
	}
	public boolean isOutlier() {
		return prelabel.equals("outlier");
	}
}
