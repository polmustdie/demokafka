package com.example.demokafka.weka;

import com.example.demokafka.model.BatchGeoData;
import com.example.demokafka.weka.nodes.WekaNode;
import com.example.demokafka.weka.utils.ARFFReader;
import com.example.demokafka.weka.utils.MeasureCalculator;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.List;

public class Algo<T extends WekaNode> {
    protected ArrayList<BatchGeoData> outliers = new ArrayList<>();
    protected ArrayList<BatchGeoData> normals = new ArrayList<>();
    protected ArrayList<BatchGeoData> nodes = new ArrayList<>();

    protected Instances dataset;

    public Algo(String path){
        ARFFReader reader = new ARFFReader(path);
        this.dataset = reader.getDataset();
    }
    public Algo(String path, boolean flag){
        ARFFReader reader = new ARFFReader(path, true); //for DBSCAN
        this.dataset = reader.getDataset();
    }

    public ArrayList<BatchGeoData> getOutliers() {
        return outliers;
    }

    public ArrayList<BatchGeoData> getNormals() {
        return normals;
    }

    public ArrayList<BatchGeoData> getNodes() {
        return nodes;
    }

    public void showResults(List<T> nodeset){
        System.out.println("\nExperiments Results of <" + dataset.relationName() + "> By Using Outlier Detection Method.");
        System.out.println("\n---------------- Detected Outliers ------------------\n");
        for(int i=0; i<nodeset.size(); i++){
            if(nodeset.get(i).isOutlier())
                System.out.println( "Label: " + nodeset.get(i).getLabel()+" x y " + nodeset.get(i).getLsAttr() + nodeset.get(i).getDate());
        }
        System.out.println("\n---------------- Detected Normals ------------------\n");
        for(int i=0; i<nodeset.size(); i++){
            if(!nodeset.get(i).isOutlier())
                System.out.println("Label: " + nodeset.get(i).getLabel()+" x y " + nodeset.get(i).getLsAttr());
        }
        System.out.println("----------------------------------");

        MeasureCalculator mc = new MeasureCalculator(nodeset);

        System.out.println("TP:" + mc.getTP());
        System.out.println("TN:" + mc.getTN());
        System.out.println("FP:" + mc.getFP());
        System.out.println("FN:" + mc.getFN());
//
//		System.out.println("PRECISION:" + mc.getPRECISION());
//		System.out.println("RECALL:" + mc.getRECALL());
//		System.out.println("F-MEASURE:" + mc.getFMEASURE());
//		System.out.println("ACCURACY:" + mc.getCORRECTRATIO());

        System.out.println("Detection Rate: " + mc.getDetectRate());
        System.out.println("FP Rate       : " + mc.getFPRate());
    }

    public double getDetectionRate(List<T> nodeset){
        MeasureCalculator mc = new MeasureCalculator(nodeset);
        return mc.getDetectRate();
    }

    public double getFPRate(List<T> nodeset){
        MeasureCalculator mc = new MeasureCalculator(nodeset);
        return mc.getFPRate();
    }

    void showArraysInfo(List<T> nodeset) {
        BatchGeoData data;
        for (int i = 0; i < nodeset.size(); i++) {

            if (nodeset.get(i).isOutlier()) {
                data = new BatchGeoData();
                data.setX(nodeset.get(i).getLsAttr().get(1));
                data.setY(nodeset.get(i).getLsAttr().get(2));
                data.setDate(nodeset.get(i).getDate());
                data.setFlag("");
                outliers.add(data);
                data.setFlag(String.valueOf(nodeset.get(i).isOutlier()));
                nodes.add(data);
            }
        }

        for (int i = 0; i < nodeset.size(); i++) {
            if (!nodeset.get(i).isOutlier()) {
                data = new BatchGeoData();
                data.setX(nodeset.get(i).getLsAttr().get(1));
                data.setY(nodeset.get(i).getLsAttr().get(2));
                data.setDate(nodeset.get(i).getDate());
                data.setFlag("");
                normals.add(data);
                data.setFlag(String.valueOf(nodeset.get(i).isOutlier()));
                nodes.add(data);
            }
        }
    }

}
