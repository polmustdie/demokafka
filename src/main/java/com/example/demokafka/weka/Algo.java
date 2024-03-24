package com.example.demokafka.weka;

import com.example.demokafka.model.BatchGeoData;
import com.example.demokafka.weka.nodes.WekaNode;
import com.example.demokafka.weka.utils.ARFFReader;
import com.example.demokafka.weka.utils.MeasureCalculator;
import lombok.Getter;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.List;
@Getter
public class Algo<T extends WekaNode> {
    protected ArrayList<BatchGeoData> outliers = new ArrayList<>();
    protected ArrayList<BatchGeoData> normals = new ArrayList<>();
    protected ArrayList<BatchGeoData> nodes = new ArrayList<>();

    protected Instances dataset;
    protected Instances datasetForDBSCAN;
    protected Instances datasetForIF;

    public Algo(String path){
        ARFFReader reader = new ARFFReader(path);
        this.dataset = reader.getDataset();
        Instances inst = new Instances(reader.getDataset());
        this.datasetForIF = inst;

    }
    public Algo(String path, boolean flag){
        ARFFReader reader = new ARFFReader(path, true); //for DBSCAN
        this.dataset = reader.getDataset();
        Instances inst = new Instances(reader.getDataset());
        this.datasetForDBSCAN = inst;
    }

    public void showResults(List<T> nodeset){
        System.out.println("\nExperiments Results of <" + dataset.relationName() + "> By Using Outlier Detection Method.");
        System.out.println("\n---------------- Detected Outliers ------------------\n");
        for(int i=0; i<nodeset.size(); i++){
            if(nodeset.get(i).isOutlier())
                System.out.println( "Label: " + nodeset.get(i).getLabel()+" x y " + nodeset.get(i).getLsAttr() + " Point id: "+ nodeset.get(i).getPointId());
        }
        System.out.println("\n---------------- Detected Normals ------------------\n");
        for(int i=0; i<nodeset.size(); i++){
            if(!nodeset.get(i).isOutlier())
                System.out.println("Label: " + nodeset.get(i).getLabel()+" x y " + nodeset.get(i).getLsAttr() + " Point id: "+ nodeset.get(i).getPointId());
        }
        System.out.println("----------------------------------");

        MeasureCalculator mc = new MeasureCalculator(nodeset);

        System.out.println("TP:" + mc.getTP());
        System.out.println("TN:" + mc.getTN());
        System.out.println("FP:" + mc.getFP());
        System.out.println("FN:" + mc.getFN());


        System.out.println("Detection Rate: " + mc.getDetectRate());
        System.out.println("FP Rate       : " + mc.getFPRate());
    }

    void showArraysInfo(List<T> nodeset) {
        for (int i = 0; i < nodeset.size(); i++) {
            if (nodeset.get(i).isOutlier()) {
                addNodeToNodeList(nodeset.get(i));
            }
        }

        for (int i = 0; i < nodeset.size(); i++) {
            if (!nodeset.get(i).isOutlier()) {
                addNodeToNodeList(nodeset.get(i));
            }
        }
    }

    private void addNodeToNodeList(T node) {
        BatchGeoData data = new BatchGeoData();
        data.setX(node.getLsAttr().get(3));
        data.setY(node.getLsAttr().get(4));
        data.setDate(node.getDate());
        data.setFlag("");
        data.setPointId(node.getPointId());
        data.setUserId(node.getUserId());
        if (node.isOutlier()){
            outliers.add(data);
        }
        else{
            normals.add(data);
        }
        data.setFlag(String.valueOf(node.isOutlier()));
        nodes.add(data);
    }

}
