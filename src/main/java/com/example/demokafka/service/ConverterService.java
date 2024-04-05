package com.example.demokafka.service;

import com.example.demokafka.model.BatchGeoData;
import com.example.demokafka.model.BatchGeoDataToController;
import com.example.demokafka.model.BatchInfoAndData;
import com.example.demokafka.model.GeoDataFlag;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

@Service
public class ConverterService {

    public BatchInfoAndData convert(BatchGeoDataToController batchGeoDataToController){
        Date date;
        BatchGeoData batchData;
        ArrayList<BatchGeoData> values = new ArrayList<>();
        for (GeoDataFlag datum : batchGeoDataToController.getData()) {
            try {
                date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                        .parse(datum.getTimestamp());
                batchData = new BatchGeoData(date, datum.getId (), datum.getUserId(), (double) datum.getLongitude(), (double) datum.getLatitude(), datum.getFlag());
                values.add(batchData);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        values.sort(Comparator.comparing(BatchGeoData::getDate));
        return new BatchInfoAndData(batchGeoDataToController.getConstants(), values);
    }

}
