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
                rs.getString("description")
        );

        attraction.setLocation(rs.getString("location_name"));
        attraction.setColorHex(rs.getString("color_hex"));

        return attraction;
    }

    //join tabel til senere
    //
    // SELECT ta.attraction_id,
    //       ta.location_id,
    //       ta.name,
    //       ta.description,
    //       l.name AS location_name
    //FROM tourist_attraction ta
    //JOIN location l ON ta.location_id = l.location_id
}
