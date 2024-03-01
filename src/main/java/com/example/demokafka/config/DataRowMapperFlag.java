package com.example.demokafka.config;

import com.example.demokafka.model.GeoDataFlag;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DataRowMapperFlag implements RowMapper<GeoDataFlag>
{
    @Override
    public GeoDataFlag mapRow(ResultSet rs, int rowNum) throws SQLException
    {
        GeoDataFlag data = new GeoDataFlag();
        data.setId(rs.getInt("id"));
        data.setUserId(rs.getInt("user_id"));
        data.setTimestamp(rs.getString("timestamp"));
        data.setLongitude(rs.getFloat("longitude"));
        data.setLatitude(rs.getFloat("latitude"));
        data.setFlag(rs.getString("flag"));
        data.setNew(rs.getBoolean("new"));

        return data;
    }
}
