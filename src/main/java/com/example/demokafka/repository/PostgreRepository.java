package com.example.demokafka.repository;


import com.example.demokafka.model.BatchGeoData;
import com.example.demokafka.model.BatchGeoDataToPostgres;
import com.example.demokafka.model.GeoData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository

public interface PostgreRepository extends CrudRepository<BatchGeoDataToPostgres, Long> {
    //naming convention?
    BatchGeoDataToPostgres findGeoDataByUserId(long id);

}