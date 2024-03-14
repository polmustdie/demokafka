package com.example.demokafka.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Component
@AllArgsConstructor
@Entity
@Table(name = "points_from_service")
public class BatchGeoDataToPostgres {
    @Id
    @GeneratedValue
    private Integer id;
    private Integer pointId;
    private Integer userId;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date date;
    private Double x;
    private Double y;
    private String flag;

    public BatchGeoDataToPostgres() {
    }

    public BatchGeoDataToPostgres(Date date, Double x, Double y, String flag, int i, int i1) {
        this.date = date;
        this.x = x;
        this.y = y;
        this.flag = flag;
        this.pointId = i;
        this.userId = i1;
    }
}

