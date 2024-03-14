package com.example.demokafka.model;

import lombok.Data;

@Data
public class GeoData {
    private int userId;

    private String date;

    private float longitude;

    private float latitude;


    @Override
    public String toString() {
        return "GeoData{" +
                "userId=" + userId +
                ", date='" + date + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }


}
