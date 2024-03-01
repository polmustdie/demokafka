package com.example.demokafka.service;


import com.example.demokafka.config.DataRowMapper;
import com.example.demokafka.config.DataRowMapperFlag;
import com.example.demokafka.model.GeoData;
import com.example.demokafka.model.GeoDataFlag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class GeoService {
    @Autowired
    private final JdbcTemplate jdbcTemplateClick;
//    @Autowired
//    private PostgreRepository postgreRepository;
//    private ClickhouseRepository clickhouseRepository;
//    public GeoService(PostgreRepository postgreRepository, JdbcTemplate jdbcTemplateClick) {
//        this.postgreRepository = postgreRepository;
////        this.clickhouseRepository = clickhouseRepository;
//        this.jdbcTemplateClick = jdbcTemplateClick;
//
//    }
    public GeoService(JdbcTemplate jdbcTemplateClick) {
//        this.clickhouseRepository = clickhouseRepository;
        this.jdbcTemplateClick = jdbcTemplateClick;

    }

//    public void save(GeoData data){
//        postgreRepository.save(data);
//    }
//
//
//    public Iterable<GeoData> getGeoData(){
//        return postgreRepository.findAll();
//    }
//
//    public GeoData getGeoDataById(int id){
//        return postgreRepository.findGeoDataByUserId(id);
//    }

    public void saveClick(GeoData data){
        String sql = "INSERT INTO points(id, date, longitude, latitude) values(?, ?, ?, ?)";
        jdbcTemplateClick.update(sql,
                data.getUserId(), data.getDate(), data.getLongitude(), data.getLatitude());
    }

    public List<GeoData> getGeoDataClick() {
        String sql = "SELECT * FROM points";

        List<GeoData> ans =  jdbcTemplateClick.query(sql, new DataRowMapper());
        for (int i = 0; i < ans.size(); i++){
            System.out.println(ans.get(i).toString());
        }
        return ans;
    }
    public List<GeoDataFlag> getGeoDataClickFlag() {
        String sql = "SELECT * FROM geo_points";

        List<GeoDataFlag> ans =  jdbcTemplateClick.query(sql, new DataRowMapperFlag());
        for (int i = 0; i < ans.size(); i++){
            System.out.println(ans.get(i).toString());
        }
        return ans;
    }

    public void saveClickFlag(GeoDataFlag data){
        String sql = "INSERT INTO geo_points(id, user_id, longitude, latitude, flag, timestamp, new) values(?, ?, ?, ?)";
        jdbcTemplateClick.update(sql,
                data.getId(), data.getUserId(), data.getLongitude(), data.getLatitude(), data.getFlag(), data.getTimestamp(),
                data.getIsNew());
    }

    public void deleteById(int id){
        var sql = "ALTER TABLE points DELETE WHERE id = ?";
        jdbcTemplateClick.update(sql, id);

    }
//    public void saveClick(GeoDataClick data){
//        clickhouseRepository.save(data);
//    }

//    public void deleteById(int id){
//        clickhouseRepository.deleteById(id);
//
//    }
}
