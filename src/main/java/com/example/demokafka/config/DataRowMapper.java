package com.example.demokafka.config;

import com.example.demokafka.model.GeoData;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DataRowMapper implements RowMapper<GeoData>
{
    @Override
    public GeoData mapRow(ResultSet rs, int rowNum) throws SQLException
    {
        GeoData data = new GeoData();
        data.setUserId(rs.getInt("id"));
        data.setDate(rs.getString("date"));
        data.setLongitude(rs.getFloat("longitude"));
        data.setLatitude(rs.getFloat("latitude"));

        return data;
    }
}
