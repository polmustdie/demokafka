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

    public GeoService(JdbcTemplate jdbcTemplateClick) {
        this.jdbcTemplateClick = jdbcTemplateClick;

    }

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
        String sql = "SELECT * FROM geo_points where new=true";

        List<GeoDataFlag> ans =  jdbcTemplateClick.query(sql, new DataRowMapperFlag());
        for (int i = 0; i < ans.size(); i++){
            System.out.println(ans.get(i).toString());
        }
        return ans;
    }

    public void updateIsNewField(List<GeoDataFlag> data, Boolean bool) {
        String sql = "ALTER TABLE geo_points UPDATE new=? where id=?";
        for (GeoDataFlag datum : data){
            jdbcTemplateClick.update(sql, bool, datum.getId());
        }
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

}
