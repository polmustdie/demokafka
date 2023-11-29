package com.example.demokafka.weka;

import com.example.demokafka.model.BatchGeoData;
import com.example.demokafka.weka.nodes.SBSNode;
import com.example.demokafka.weka.utils.ARFFReader;
import com.example.demokafka.weka.utils.MeasureCalculator;
import weka.clusterers.ClusterEvaluation;
import weka.clusterers.DBSCAN;
import weka.core.Instance;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.List;

// dbscan - clustering algo
public class DBSCANs extends Algo<SBSNode>{
	// Epsilon in DBSCAN>??????????????????
	public static final double EPS = 0.2;

	public static final int MIN_POINTS = 5;
	
	private static List<SBSNode> nodeset = new ArrayList<>();

//	private ArrayList<BatchGeoData> outliers = new ArrayList<>();
//	private ArrayList<BatchGeoData> normals = new ArrayList<>();
//	private ArrayList<BatchGeoData> nodes = new ArrayList<>();

	public List<SBSNode> getNodeset() {
		return nodeset;
	}

	public DBSCANs(String path){
		super(path, true);
		nodeset.clear();
//		dataset.deleteAttributeAt(dataset.numAttributes()-1); //DBSCAN is a unsuperviesd method.
		for(int i=0; i<dataset.numInstances(); i++){
			Instance currentInstance = dataset.get(i);
			SBSNode node = new SBSNode(currentInstance);
			nodeset.add(node);
		}
		
		try {
			clusteringByDBSCAN();
			showArraysInfo(nodeset);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
//	private void showArraysInfo() {
//		BatchGeoData data;
//		for (int i = 0; i < nodeset.size(); i++) {
//
//			if (nodeset.get(i).isOutlier()) {
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
//		for (int i = 0; i < nodeset.size(); i++) {
//			if (!nodeset.get(i).isOutlier()) {
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
//	}
	
	// probability of each node
	private void clusteringByDBSCAN() throws Exception{
		DBSCAN dbscan = new DBSCAN();
		dbscan.setEpsilon(EPS);
		dbscan.setMinPoints(MIN_POINTS);
		
		dbscan.buildClusterer(dataset);
		ClusterEvaluation eval = new ClusterEvaluation();
		eval.setClusterer(dbscan);
		eval.evaluateClusterer(dataset);
		double[] num = eval.getClusterAssignments();
//		System.out.println(num.length);
		
		for(int i=0; i<nodeset.size(); i++){
			SBSNode currentNode = nodeset.get(i);
			currentNode.setClusterIndex(num[i]);
			if(num[i] < 0){
				currentNode.setPrelabel("outlier");
			}
		}
		
	}



//	public void showResults(){
//		System.out.println("\nExperiments Results of <" + dataset.relationName() + "> By Using DBSCAN Outlier Detection Method.");
//		System.out.println("\n---------------- Detected Outliers ------------------\n");
//		for(int i=0; i<nodeset.size(); i++){
//			if(nodeset.get(i).isOutlier())
//				System.out.println(i + " " + nodeset.get(i).getClusterIndex() + ", Label: " + nodeset.get(i).getLabel());
//		}
//		System.out.println("\n---------------- Detected Normals ------------------\n");
//		for(int i=0; i<nodeset.size(); i++){
//			if(!nodeset.get(i).isOutlier())
//				System.out.println(i + " " +nodeset.get(i).getClusterIndex() + ", Label: " + nodeset.get(i).getLabel());
//		}
//		System.out.println("----------------------------------");
//
//		MeasureCalculator mc = new MeasureCalculator(nodeset);
//
//		System.out.println("TP:" + mc.getTP());
//		System.out.println("TN:" + mc.getTN());
//		System.out.println("FP:" + mc.getFP());
//		System.out.println("FN:" + mc.getFN());
////
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

