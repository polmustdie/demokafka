package com.example.demokafka.weka;

import com.example.demokafka.weka.comparators.WeightComparator;
import com.example.demokafka.weka.nodes.HILNode;
import com.example.demokafka.weka.utils.DistanceCalculator;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/*
 detects outliers by calculating weight of each data point, the weight is the sum of distances
  between one point to its k-nearest neighbors. Thus, those data points who have the higher weight are
  more likely to be the outliers.
  1. calculating distances of any pair of points.
  2. getting K<-nearest neighbors of each point.
  3. counting the weight of each point.
  4. ranking the points in terms of weights, the top-N points are detected as outliers.
 */
public class HilOut extends Algo<HILNode>{
	public static final int K = 5;
	public static  int k_test;
	// top n outliers
	public static final double N = 0.5;
	public static  double n_test;
	private static final  List<HILNode> nodeset = new ArrayList<>();
	private static final List<double[]> lsDistance = new ArrayList<>();

	public HilOut(String path, ArrayList<Object> constants) throws ParseException {
		super(path);
		k_test = Integer.parseInt(constants.get(0).toString());
		n_test = Double.parseDouble(constants.get(1).toString());

		nodeset.clear();
		lsDistance.clear();

		for(int i=0; i<dataset.numInstances(); i++){
			HILNode node = new HILNode(dataset.get(i));
			nodeset.add(node);
		}
		
		calculateKNeighbors();
		
		rankingByWeights();

		showArraysInfo(nodeset);
	}

	public List<HILNode> getNodeset() {
		return nodeset;
	}

	private void calculateKNeighbors(){
		int size = nodeset.size();
		
		// distance between two nodes
		for(int i=0; i<size; i++){ // for each instance
			double[] lsEach = new double[size];
			for(int j=0; j<size; j++){ // calculate distance from other instance
				lsEach[j] = nodeset.get(i).getDistanceToOther(nodeset.get(j));
			}
			lsDistance.add(lsEach);
		}
		
		// k nearest neighbors for each instance
		for(int i=0; i<lsDistance.size(); i++){
			double kdis = DistanceCalculator.findKDistance(lsDistance.get(i), k_test);
			HILNode currentInstance = nodeset.get(i);
			for(int j=0; j<lsDistance.size(); j++){
				if(currentInstance.getDistanceToOther(nodeset.get(j)) <= kdis && j != i){
					currentInstance.setNeighbor(nodeset.get(j));
				}	
			}	
			currentInstance.setWeight();
		}		
	}
	
	private void rankingByWeights(){
		nodeset.sort(new WeightComparator());
		int outlierNum = (int)(nodeset.size()*n_test);
		
		for(int i=0; i<outlierNum; i++){
			nodeset.get(i).setPrelabel("outlier");
		}
		
	}
}


