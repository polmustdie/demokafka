package com.example.demokafka.weka;

import com.example.demokafka.weka.comparators.LOFComparator;
import com.example.demokafka.weka.nodes.LOFNode;
import lombok.Getter;
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
@Getter
public class LocalOutlierFactor extends Algo<LOFNode>{
	// n outliers
//	private static final double N = 0.15;
	private final double n_test;
	// lower bound used to be 20
//	private static final String LOWER_BOUND = "5";
	private final String lower_bound_test;
	// upper bound used to be 30
//	private static final String UPPER_BOUND = "10";
	private final String upper_bound_test;
	private final List<LOFNode> nodeset = new ArrayList<>();
	private final ArrayList<LOFNode> values = new ArrayList<>();


	public LocalOutlierFactor(String path, ArrayList<Object> constants) throws ParseException {

		super(path);
		nodeset.clear();
		n_test = Double.parseDouble(constants.get(0).toString());
		lower_bound_test = constants.get(1).toString();
		upper_bound_test = constants.get(2).toString();

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
		lof.setMinPointsUpperBound(String.valueOf(nodeset.size() - 2));
		LinearNNSearch searcher = new LinearNNSearch();
//		searcher.setDistanceFunction(new ManhattanDistance());
		searcher.setDistanceFunction(new EuclideanDistance());
		lof.setNNSearch(searcher);
		lof.setInputFormat(dataset);
		dataset = Filter.useFilter(dataset, lof);
		
		for(int i = 0; i< nodeset.size(); i++){
			nodeset.get(i).setLof(dataset.get(i).value(dataset.numAttributes()-1));
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

