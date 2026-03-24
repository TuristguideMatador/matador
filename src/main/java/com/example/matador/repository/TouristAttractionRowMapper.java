package com.example.matador.repository;

import com.example.matador.model.TouristAttraction;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TouristAttractionRowMapper implements RowMapper<TouristAttraction> {
    @Override
    public TouristAttraction mapRow(ResultSet rs, int rowNum) throws SQLException {
        TouristAttraction attraction = new TouristAttraction(
                rs.getInt("attraction_id"),
                rs.getInt("location_id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getString("location_name"),
                rs.getString("color_hex")
        );
        return attraction;
    }
}
