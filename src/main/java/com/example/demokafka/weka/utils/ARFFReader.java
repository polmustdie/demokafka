package com.example.demokafka.weka.utils;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

import java.util.HashSet;
import java.util.Set;

@Getter
@Slf4j
public class ARFFReader {

	private Instances dataset;

	private double[] mu;
	private double[] std;
	private Set<String> labels = new HashSet<>();

	public ARFFReader(String path){
		try {

			dataset = DataSource.read(path);
			dataset.setClassIndex(dataset.numAttributes()-1);

		} catch (Exception e) {
			log.error("Loading files error!");
			e.printStackTrace();
		}

		for(Instance ins: dataset){
			String label = ins.stringValue(dataset.classAttribute());
			labels.add(label);
		}
		
		//calculating mu std
		try{
			setMu();
			setStd();
		}catch(Exception e){
			log.error("Calculating parameters error!");
			e.printStackTrace();
		}
		
	}
	
	//for DBSCAN
	public ARFFReader(String path, boolean flag){
		try {
			dataset = DataSource.read(path);

		} catch (Exception e) {
			log.error("Loading files error!");
			e.printStackTrace();
		}
		
		try{
			setMu();
			setStd();
		}catch(Exception e){
			log.error("Calculating parameters error!");
			e.printStackTrace();
		}
		
	}
	
	public void setMu(){
		int attrNum = this.dataset.numAttributes();
		int insNum = this.dataset.numInstances();
		double[] mus = new double[attrNum - 1];
		
		for(int i=0; i<attrNum-1; i++){
			double sum = 0.0d;
			for(int j=0; j <insNum; j++){
				sum += dataset.get(j).value(i);
			}
			mus[i] = (sum*1.0)/(insNum*1.0);
		}
		
		this.mu = mus;
	}
	
	// std for each attribute
	public void setStd(){
		int attrNum = this.dataset.numAttributes();
		int insNum = this.dataset.numInstances();
		double[] stds = new double[attrNum - 1];
		
		for(int i=0; i<attrNum-1; i++){
			double delt = 0.0d;
			for(int j=0; j <insNum; j++){
				delt += Math.pow((dataset.get(j).value(i) - mu[i]), 2);
			}
			stds[i] = Math.sqrt((delt*1.0)/((insNum-1)*1.0));
		}
		
		this.std = stds;
		
	}
	
	public void showDataset(){
		log.info("----------   Dataset Basic Information   ----------");
		log.info("----------   Dataset Basic Information   ----------");
		log.info("(1) Relation Name : " + dataset.relationName());
		log.info("(2) Instances     : " + dataset.numInstances());
		log.info("(3) Attributes    : " + (dataset.numAttributes()-1));
		log.info("(4) Class Labels  : " + labels);
	}

}
