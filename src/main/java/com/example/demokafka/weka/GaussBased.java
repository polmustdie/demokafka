package com.example.demokafka.weka;


import com.example.demokafka.weka.comparators.ProbabilityComparator;
import com.example.demokafka.weka.nodes.GAUNode;
import com.example.demokafka.weka.utils.ARFFReader;
import lombok.Getter;
import weka.core.Instance;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*

  It assumes that the instance distribution is under the Gauss Distribution, and the instances with
  low probability is detected as the outliers.

  1. Get mean value <b>&mu;</b> of each dimension (feature).
  2. Get the standard deviation sigma of each dimension (feature).
  3. Get p(x) of each instance x, and select the top-N< instances who
  has the smallest p(x), they are detected as an outlier. Note that p(x) is defined as follows,

  p(x) = 1/(sqrt(2&pi;)*sigma;j) * exp((-1)*(xj-&mu;j)^2/(2*&sigma;^2))
 */
@Getter
public class GaussBased extends Algo<GAUNode> {
	//top n outliers
//	private static final double N = 0.1;
	private final double n_test;
	private List<GAUNode> nodeset = new ArrayList<>();

	public GaussBased(String path, ArrayList<Object> constants) throws ParseException {
		super(path);
		nodeset.clear();
		n_test = Double.parseDouble(constants.get(0).toString());

		for (int i = 0; i < dataset.numInstances(); i++) {
			Instance currentInstance = dataset.get(i);
			GAUNode node = new GAUNode(currentInstance);
			nodeset.add(node);
		}

		calculateProbability(path);
		rankingByProbability();
		showArraysInfo(nodeset);
	}

	// probability of each node
	private void calculateProbability(String path) {

		ARFFReader reader = new ARFFReader(path);
		double[] mus = reader.getMu();
		double[] stds = reader.getStd();

		for (int i = 0; i < nodeset.size(); i++) {
			List<Double> lsAttr = nodeset.get(i).getLsAttr();
			double pi = 1.0d;

			for (int j = 0; j < dataset.numAttributes() - 1; j++) {
				double xJ = lsAttr.get(j);
				double currentMu = mus[j];
				double currentStd = stds[j];
				if (currentStd - 0 == 0) { // if some dimension's std is equal to 0.
					continue;
				}
				pi *= (1.0 / (Math.sqrt(2.0 * Math.PI) * currentStd)) * Math.exp((-1.0) * (xJ - currentMu) * (xJ - currentMu) / (2 * currentStd * currentStd));
			}
			nodeset.get(i).setProbability(pi);
		}
	}

	private void rankingByProbability() {
		Collections.sort(nodeset, new ProbabilityComparator());
		int topNum = (int) (n_test * nodeset.size());
		for (int i = 0; i < topNum; i++) {
			nodeset.get(i).setPrelabel("outlier");
		}
	}
}

