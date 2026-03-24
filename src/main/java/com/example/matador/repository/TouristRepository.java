package com.example.matador.repository;

import com.example.matador.model.TouristAttraction;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TouristRepository {
    private final JdbcTemplate jdbcTemplate;

    public TouristRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<TouristAttraction> findAll() {
        String sql = """
        SELECT 
            tourist_attraction.attraction_id,
            tourist_attraction.location_id,
            tourist_attraction.name, 
            tourist_attraction.description,
            location.name AS location_name,
            color.hex AS color_hex
        FROM
            tourist_attraction
        JOIN location
            ON tourist_attraction.location_id = location.location_id
        JOIN color
            ON location.color_id = color.color_id
        """;

        List<TouristAttraction> attractions = jdbcTemplate.query(sql, new TouristAttractionRowMapper());

        for (TouristAttraction attraction : attractions) {
            attraction.setTags(getTagsForAttraction(attraction.getAttractionId()));
        }

        return attractions;
    }

    // get = NAME
    public TouristAttraction getTouristAttractionByName(String name) {
        String sql = """
        SELECT tourist_attraction.attraction_id,
               tourist_attraction.location_id,
               tourist_attraction.name,
               tourist_attraction.description,
               location.name AS location_name,
               color.hex AS color_hex
        FROM tourist_attraction
        JOIN location ON tourist_attraction.location_id = location.location_id
        JOIN color ON location.color_id = color.color_id
        WHERE tourist_attraction.name = ?
        """;

        List<TouristAttraction> result = jdbcTemplate.query(sql, new TouristAttractionRowMapper(), name);

        if (result.isEmpty()) {
            return null;
        }


        TouristAttraction attraction = result.get(0);
        attraction.setTags(getTagsForAttraction(attraction.getAttractionId()));
        return attraction;
    }


    // get location id by name
    public int getLocationIdByName(String locationName) {
        String sql = "SELECT location_id FROM location WHERE name = ?";
        Integer locationId = jdbcTemplate.queryForObject(sql, Integer.class, locationName);
        return locationId;
    }



    // get locations
    public List<String> getLocations() {
        String sql = """
            SELECT name FROM location
            """;

        return jdbcTemplate.queryForList(sql, String.class);
    }


    public List<String> getAllTags() {
        String sql = """
            SELECT tag_name FROM tag
            """;

        return jdbcTemplate.queryForList(sql, String.class);
    }


    public List<String> getTagsForAttraction(int attractionId) {
        String sql = """
        SELECT tag.tag_name
        FROM attraction_tag
        JOIN tag ON tag.tag_id = attraction_tag.tag_id
        WHERE attraction_tag.attraction_id = ?
        """;

        List<String> tagNames = jdbcTemplate.queryForList(sql, String.class, attractionId);
        return tagNames;
    }


    public TouristAttraction addTouristAttraction(TouristAttraction touristAttraction) {
        String sql = """
        INSERT INTO tourist_attraction (name, description, location_id)
        VALUES (?, ?, ?)
        """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            var ps = connection.prepareStatement(sql, new String[]{"attraction_id"});
            ps.setString(1, touristAttraction.getName());
            ps.setString(2, touristAttraction.getDescription());
            ps.setInt(3, touristAttraction.getLocationId());
            return ps;
        }, keyHolder);

        int attractionId = keyHolder.getKey().intValue();
        //touristAttraction.setAttractionId(attractionId);

        saveTagsForAttraction(attractionId, touristAttraction.getTags());

        return touristAttraction;

    }

    // save tags
    private void saveTagsForAttraction(int attractionId, List<String> tags) {
        String findTagIdSql = """
            SELECT tag_id FROM tag WHERE tag_name = ?
            """;
        String insertJoinSql = """
            INSERT INTO attraction_tag (attraction_id, tag_id) VALUES (?, ?)
            """;

        for (String tag : tags) {
            Integer tagId = jdbcTemplate.queryForObject(findTagIdSql, Integer.class, tag);
            jdbcTemplate.update(insertJoinSql, attractionId, tagId);
        }
    }

    // update
    public void update(TouristAttraction updatedTouristAttraction) {
        String sql = """
            UPDATE tourist_attraction
            SET name = ?, description = ?, location_id = ? 
            WHERE attraction_id = ?
        """;

        jdbcTemplate.update(sql,
                updatedTouristAttraction.getName(),
                updatedTouristAttraction.getDescription(),
                updatedTouristAttraction.getLocationId(),
                updatedTouristAttraction.getAttractionId());

        String deleteTagsSql = "DELETE FROM attraction_tag WHERE attraction_id = ?";
        jdbcTemplate.update(deleteTagsSql, updatedTouristAttraction.getAttractionId());

        saveTagsForAttraction(updatedTouristAttraction.getAttractionId(), updatedTouristAttraction.getTags());
    }

    public void deleteByName(String name) {
        TouristAttraction attraction = getTouristAttractionByName(name);
        if (attraction == null) {
            return;
        }

        String deleteJoinSql = """
            DELETE FROM attraction_tag WHERE attraction_id = ?
            """;
        jdbcTemplate.update(deleteJoinSql, attraction.getAttractionId());

        String deleteAttractionSql = """
            DELETE FROM tourist_attraction WHERE attraction_id = ?
            """;
        jdbcTemplate.update(deleteAttractionSql, attraction.getAttractionId());
    }



}
