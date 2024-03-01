package com.example.demokafka.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "geo_points")
public class GeoDataFlag {
    @Id
    @Column(name="id")
    private int id;
    @Column(name="user_id")
    private int userId;
    @Column(name="timestamp")
    private String timestamp;
    @Column(name="longitude")
    private float longitude;
    @Column(name="latitude")
    private float latitude;

    @Column(name="new")
    private boolean isNew;
    @Column(name="flag")
    private String flag;


    @Override
    public String toString() {
        return "GeoDataFlag{" +
                "id=" + id +
                ", userId=" + userId +
                ", timestamp='" + timestamp + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", isNew=" + isNew +
                ", flag='" + flag + '\'' +
                '}';
    }

    public boolean getIsNew() {
        return this.isNew;
    }
}
