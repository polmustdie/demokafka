package com.example.demokafka.repository;


import com.example.demokafka.model.BatchGeoDataToPostgres;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository

public interface PostgreRepository extends CrudRepository<BatchGeoDataToPostgres, Long> {
    BatchGeoDataToPostgres findGeoDataByUserId(long id);

}