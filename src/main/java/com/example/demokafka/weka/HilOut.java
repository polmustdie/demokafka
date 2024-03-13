package com.example.demokafka.weka;


import com.example.demokafka.model.BatchGeoData;
import com.example.demokafka.weka.comparators.WeightComparator;
import com.example.demokafka.weka.nodes.HILNode;
import com.example.demokafka.weka.utils.ARFFReader;
import com.example.demokafka.weka.utils.DistanceCalculator;
import com.example.demokafka.weka.utils.MeasureCalculator;
import weka.core.Instance;
import weka.core.Instances;

import java.lang.reflect.Array;
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

//	private Instances dataset;
	
	public static final int K = 5;
	public static  int k_test;
	// top n outliers
	public static final double N = 0.5;
	public static  double n_test;
	private static final  List<HILNode> nodeset = new ArrayList<>();
	
	private static final List<double[]> lsDistance = new ArrayList<>();
//	private ArrayList<BatchGeoData> outliers = new ArrayList<>();
//	private ArrayList<BatchGeoData> normals = new ArrayList<>();
//	private ArrayList<BatchGeoData> nodes = new ArrayList<>();

//	public List<BatchGeoData> getOutliers() {
//		return outliers;
//	}
//
//	public List<BatchGeoData> getNormals() {
//		return normals;
//	}

	public HilOut(String path, ArrayList<Object> constants) throws ParseException {
		super(path);
		k_test = Integer.parseInt(constants.get(0).toString());
		n_test = Double.parseDouble(constants.get(1).toString());

		nodeset.clear();
		lsDistance.clear();
		
//		ARFFReader reader = new ARFFReader(path);
//		dataset = reader.getDataset();
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

//	private void showArraysInfo() {
//		BatchGeoData data;
//		for(int i=0; i<nodeset.size(); i++){
//
//			if(nodeset.get(i).isOutlier()) {
//				data = new BatchGeoData();
//				data.setX(nodeset.get(i).getAttr().get(0));
//				data.setY(nodeset.get(i).getAttr().get(1));
//				data.setFlag("");
////			outliers.add(nodeset.get(i).getAttr());
//				outliers.add(data);
//				data.setFlag(String.valueOf(nodeset.get(i).isOutlier()));
//				nodes.add(data);
//			}
//
//		}
//		for(int i=0; i<nodeset.size(); i++){
//			if(!nodeset.get(i).isOutlier()) {
//				data = new BatchGeoData();
//				data.setX(nodeset.get(i).getAttr().get(0));
//				data.setY(nodeset.get(i).getAttr().get(1));
//				data.setFlag("");
//				normals.add(data);
//				data.setFlag(String.valueOf(nodeset.get(i).isOutlier()));
//				nodes.add(data);
//			}
//
////				normals.add(nodeset.get(i).getAttr());
//		}
//
//	}

	public Instance getIns(int index){
		return dataset.get(index);
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
	
//	public void showResults(){
//		System.out.println("Experiments Results of <" + dataset.relationName() + "> By HilOut Outlier Detection Method.");
//		System.out.println("\n---------------- Detected Outliers ------------------\n");
//		for(int i=0; i<nodeset.size(); i++){
//			if(nodeset.get(i).isOutlier())
//				System.out.println(i+" "+"weight: " + nodeset.get(i).getWeight() + ", Label: " + nodeset.get(i).getLabel());
//		}
//		System.out.println("\n---------------- Detected Normals ------------------\n");
//		for(int i=0; i<nodeset.size(); i++){
//			if(!nodeset.get(i).isOutlier())
//				System.out.println(i+" "+"weight: " + nodeset.get(i).getWeight() + ", Label: " + nodeset.get(i).getLabel());
//		}
//		System.out.println("----------------------------------");
//
//		MeasureCalculator mc = new MeasureCalculator(nodeset);
//
//		System.out.println("TP:" + mc.getTP());
//		System.out.println("TN:" + mc.getTN());
//		System.out.println("FP:" + mc.getFP());
//		System.out.println("FN:" + mc.getFN());
//
////		System.out.println("PRECISION:" + mc.getPRECISION());
////		System.out.println("RECALL:" + mc.getRECALL());
////		System.out.println("F-MEASURE:" + mc.getFMEASURE());
////		System.out.println("ACCURACY:" + mc.getCORRECTRATIO());
//
//		System.out.println("Detection Rate: " + mc.getDetectRate());
//		System.out.println("FP Rate       : " + mc.getFPRate());
//
//	}
	
//	public double getDetectionRate(){
//		MeasureCalculator mc = new MeasureCalculator(nodeset);
//		return mc.getDetectRate();
//	}
//
//	public double getFPRate(){
//		MeasureCalculator mc = new MeasureCalculator(nodeset);
//		return mc.getFPRate();
//	}
}


