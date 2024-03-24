package com.example.demokafka.weka;

import com.example.demokafka.weka.nodes.SBSNode;
import lombok.Data;
import lombok.Getter;
import weka.clusterers.ClusterEvaluation;
import weka.clusterers.DBSCAN;
import weka.core.Instance;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

// dbscan - clustering algo
@Getter
public class DBSCANs extends Algo<SBSNode>{
	// Epsilon in DBSCAN>??????????????????
//	private static final double EPS = 0.2;
	private final double eps_test;

//	private static final int MIN_POINTS = 5;
	private final int min_points_test;
	private final List<SBSNode> nodeset = new ArrayList<>();

	public DBSCANs(String path, ArrayList<Object> constants) throws ParseException {
		super(path, true);
		eps_test = Double.parseDouble(constants.get(0).toString());
		min_points_test = Integer.parseInt(constants.get(1).toString());

		nodeset.clear();
		datasetForDBSCAN.deleteAttributeAt(1);
		datasetForDBSCAN.deleteAttributeAt(1);
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
	
	// probability of each node
	private void clusteringByDBSCAN() throws Exception{
		DBSCAN dbscan = new DBSCAN();
		dbscan.setEpsilon(eps_test);
		dbscan.setMinPoints(min_points_test);
		dbscan.buildClusterer(datasetForDBSCAN);
		ClusterEvaluation eval = new ClusterEvaluation();
		eval.setClusterer(dbscan);
		eval.evaluateClusterer(datasetForDBSCAN);
		double[] num = eval.getClusterAssignments();
		if (dbscan.numberOfClusters() != 0){
			for(int i=0; i<nodeset.size(); i++){
				SBSNode currentNode = nodeset.get(i);
				currentNode.setClusterIndex(num[i]);
				if(num[i] < 0){
					currentNode.setPrelabel("outlier");
				}
			}
		}
	}
}

