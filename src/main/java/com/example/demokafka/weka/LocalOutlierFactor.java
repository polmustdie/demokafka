package com.example.demokafka.weka;

import com.example.demokafka.weka.comparators.LOFComparator;
import com.example.demokafka.weka.nodes.LOFNode;
import weka.core.EuclideanDistance;
import weka.core.Instance;
import weka.core.neighboursearch.LinearNNSearch;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.LOF;

import java.text.ParseException;
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
	ArrayList<LOFNode> values = new ArrayList<>();
	// n outliers
	public static final double N = 0.15;
	public static double n_test;
	// lower bound used to be 20
	public static final String LOWER_BOUND = "5";
	public static String lower_bound_test;
	// upper bound used to be 30
	public static final String UPPER_BOUND = "10";
	public static String upper_bound_test;

	private static List<LOFNode> nodeset = new ArrayList<>();
	public List<LOFNode> getNodeset() {
		return nodeset;
	}

	public LocalOutlierFactor(String path, ArrayList<Object> constants) throws ParseException {

		super(path);
		nodeset.clear();
		n_test = Double.parseDouble(constants.get(0).toString());
		lower_bound_test = constants.get(1).toString();
		upper_bound_test = constants.get(2).toString();
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
		lof.setMinPointsLowerBound(lower_bound_test);
//		lof.setMinPointsUpperBound(String.valueOf(nodeset.size() - 2));
		lof.setMinPointsUpperBound(upper_bound_test);
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
		int topNum = (int)(n_test * nodeset.size());
		
		for(int i=0; i<topNum; i++){
			nodeset.get(i).setPrelabel("outlier");
			values.add(nodeset.get(i));
		}
	}
}

