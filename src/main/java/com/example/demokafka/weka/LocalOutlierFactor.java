package com.example.demokafka.weka;

//import org.example.comparators.LOFComparator;
//import org.example.nodes.LOFNode;
import com.example.demokafka.model.BatchGeoData;
import com.example.demokafka.weka.comparators.LOFComparator;
import com.example.demokafka.weka.nodes.LOFNode;
import com.example.demokafka.weka.utils.ARFFReader;
import com.example.demokafka.weka.utils.MeasureCalculator;
import weka.core.EuclideanDistance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.neighboursearch.LinearNNSearch;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.LOF;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 1. Get k-distance of each instance.
 2. Get K-nearest neighbors of each instance.
 3. Get reach-distance of any pair of instances.
 4. Get local reach-ability density of each instance.
 5. Get LOF factor of each instance, the top-N instances with high LOF are detected as outliers.
 */
public class LocalOutlierFactor extends Algo<LOFNode>{
//	private Instances dataset;
	ArrayList<LOFNode> values = new ArrayList<>();
	// n outliers
	public static final double N = 0.15;
	// lower bound used to be 20
	public static final String LOWER_BOUND = "5";



	// upper bound used to be 30
	public static final String UPPER_BOUND = "10";

	private static List<LOFNode> nodeset = new ArrayList<>();
	public List<LOFNode> getNodeset() {
		return nodeset;
	}

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
//
//	public List<BatchGeoData> getNodes() {
//		return nodes;
//	}
	
	public LocalOutlierFactor(String path){
		super(path);
		nodeset.clear();
//
//		ARFFReader reader = new ARFFReader(path);
//		dataset = reader.getDataset();
		for(int i=0; i<dataset.numInstances(); i++){
			Instance currentInstance = dataset.get(i);
			LOFNode node = new LOFNode(currentInstance);
			nodeset.add(node);
		}
		
		try {
			calculateLOF();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		rankingByLOF();

		showArraysInfo(nodeset);
		
	}

	private void calculateLOF() throws Exception{

		LOF lof = new LOF();
		lof.setMinPointsLowerBound(LOWER_BOUND);
//		lof.setMinPointsUpperBound(String.valueOf(nodeset.size() - 2));
		lof.setMinPointsUpperBound(UPPER_BOUND);
//		lof.setNNSearch();
		LinearNNSearch searcher = new LinearNNSearch();
//		searcher.setDistanceFunction(new ManhattanDistance());
		searcher.setDistanceFunction(new EuclideanDistance());
		lof.setNNSearch(searcher);
		lof.setInputFormat(dataset);
		dataset = Filter.useFilter(dataset, lof);
		
		for(int i = 0; i< nodeset.size(); i++){
			
			nodeset.get(i).setLOF(dataset.get(i).value(dataset.numAttributes()-1));
			
		}
	}
	
	private void rankingByLOF(){
		Collections.sort(nodeset, new LOFComparator());
		int topNum = (int)(N * nodeset.size());
		
		for(int i=0; i<topNum; i++){
			nodeset.get(i).setPrelabel("outlier");
			values.add(nodeset.get(i));
		}
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
//
//	public void showResults(){
//		System.out.println("\nExperiments Results of <" + dataset.relationName() + "> By Using LOF Outlier Detection Method.");
//		System.out.println("\n---------------- Detected Outliers ------------------\n");
//		for(int i = 0; i< nodeset.size(); i++){
//			if(nodeset.get(i).isOutlier())
//				System.out.println("lof: " + nodeset.get(i).getLOF() + ", Label: " + nodeset.get(i).getLabel());
//		}
//		System.out.println("\n---------------- Detected Normals ------------------\n");
//		for(int i = 0; i< nodeset.size(); i++){
//			if(!nodeset.get(i).isOutlier())
//				System.out.println("lof: " + nodeset.get(i).getLOF() + ", Label: " + nodeset.get(i).getLabel());
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
//	}
//
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

