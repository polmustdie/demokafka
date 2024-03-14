package com.example.demokafka.weka;

import com.example.demokafka.weka.nodes.IFNode;
import weka.classifiers.misc.IsolationForest;
import weka.core.Instance;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/*
  each data point will be assigned an anomaly score s in the range between 0 and 1.
  If the s in (0.5, 1], then this point have high probability to be detected as an outlier;
  else if the s in [0, 0.5), then this point can be regarded as an normal.

  1. Randomly selecting some subsets of dataset.
  2. Using these subsets to construct iTrees by randomly split a randomly selected feature.
  3. The nodes(instances) that have the longer average path length are detected as outliers.
 */
public class IsolationForests extends Algo<IFNode>{
	
	//number of instances in itree
	public static final int INSTANCES_NUMBER = 30;
	public static  int instances_number_test;
	// number of trees
	public static final int TREES_NUMBER = 15;
	public static  int trees_number_test;

	// threshold
	public static final double S_THRESHOLD = 0.5;
	public static  double s_threshold_test;
	private static List<IFNode> nodeset = new ArrayList<>();

	public List<IFNode> getNodeset() {
		return nodeset;
	}
	
	public IsolationForests(String path, ArrayList<Object> constants) throws ParseException {
		super(path);
		nodeset.clear();
		instances_number_test = Integer.parseInt(constants.get(0).toString());
		trees_number_test = Integer.parseInt(constants.get(1).toString());
		s_threshold_test = Double.parseDouble(constants.get(2).toString());
		
		for(int i=0; i<dataset.numInstances(); i++){
			Instance currentInstance = dataset.get(i);
			IFNode node = new IFNode(currentInstance);
			nodeset.add(node);
		}
		
		try {
			setAnomalyScore();
			showArraysInfo(nodeset);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void setAnomalyScore() throws Exception{
		
		IsolationForest iforest = new IsolationForest();
		iforest.setNumTrees(trees_number_test);
		iforest.setSubsampleSize(instances_number_test);
		iforest.buildClassifier(dataset);
		
		for(int i = 0; i< nodeset.size(); i++){
			double score = iforest.distributionForInstance(dataset.get(i))[1];
			nodeset.get(i).setScore(score);
			if(score >= s_threshold_test){
				nodeset.get(i).setPrelabel("outlier");
			}
		}
	}
	
}

