package com.example.demokafka.model;

//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;
import lombok.Data;

@Data
//@Entity
//@Table(name = "points")
public class GeoData {
//    @Id
//    @Column(name="id")
    private int userId;
//    @Column(name="date")
    private String date;
//    @Column(name="longitude")
    private float longitude;
//    @Column(name="latitude")
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
