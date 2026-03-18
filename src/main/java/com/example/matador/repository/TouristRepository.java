package com.example.matador.repository;

import com.example.matador.model.Tags;
import com.example.matador.model.TouristAttraction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TouristRepository {
    @Value("${spring.datasource.url}")
    private String dbUrl;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    private final JdbcTemplate jdbcTemplate;

    public TouristRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // alle queries er rent gæt lige nu
    // opfølgning med Erik kl 14

    // find alle fra join table
    public List<TouristAttraction> findAll() {
        String sql = """
        SELECT ta.attraction_id,
               ta.location_id,
               ta.name,
               ta.description,
               l.name AS location_name,
               c.hex AS color_hex
        FROM tourist_attraction ta
        JOIN location l ON ta.location_id = l.location_id
        JOIN color c ON l.color_id = c.color_id
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
        SELECT ta.attraction_id,
               ta.location_id,
               ta.name,
               ta.description,
               l.name AS location_name,
               c.hex AS color_hex
        FROM tourist_attraction ta
        JOIN location l ON ta.location_id = l.location_id
        JOIN color c ON l.color_id = c.color_id
        WHERE ta.name = ?
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
        String sql = "SELECT name FROM location ORDER BY name";
        return jdbcTemplate.queryForList(sql, String.class);
    }

    // get tags
    public List<Tags> getTagsForAttraction(int attractionId) {
        String sql = """
        SELECT t.tag_name
        FROM tag t
        JOIN attraction_tag at ON t.tag_id = at.tag_id
        WHERE at.attraction_id = ?
        """;

        List<String> tagNames = jdbcTemplate.queryForList(sql, String.class, attractionId);

        List<Tags> tags = new ArrayList<>();
        for (String tagName : tagNames) {
            tags.add(Tags.valueOf(tagName));
        }

        return tags;
    }

    // add
    public TouristAttraction addTouristAttraction(TouristAttraction touristAttraction) {
        String sql = """
        INSERT INTO tourist_attraction (location_id, name, description)
        VALUES (?, ?, ?)
        """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            var ps = connection.prepareStatement(sql, new String[]{"attraction_id"});
            ps.setInt(1, touristAttraction.getLocationId());
            ps.setString(2, touristAttraction.getName());
            ps.setString(3, touristAttraction.getDescription());
            return ps;
        }, keyHolder);

        int attractionId = keyHolder.getKey().intValue();
        touristAttraction.setAttractionId(attractionId);

        saveTagsForAttraction(attractionId, touristAttraction.getTags());

        return touristAttraction;
    }

    // save tags
    private void saveTagsForAttraction(int attractionId, List<Tags> tags) {
        String findTagIdSql = "SELECT tag_id FROM tag WHERE tag_name = ?";
        String insertJoinSql = "INSERT INTO attraction_tag (attraction_id, tag_id) VALUES (?, ?)";

        for (Tags tag : tags) {
            Integer tagId = jdbcTemplate.queryForObject(findTagIdSql, Integer.class, tag.name());
            jdbcTemplate.update(insertJoinSql, attractionId, tagId);
        }
    }

    // update
    public void update(TouristAttraction updatedTouristAttraction) {
        String sql = """
        UPDATE tourist_attraction
        SET location_id = ?, description = ?
        WHERE attraction_id = ?
        """;

        jdbcTemplate.update(sql,
                updatedTouristAttraction.getLocationId(),
                updatedTouristAttraction.getDescription(),
                updatedTouristAttraction.getAttractionId());

        String deleteTagsSql = "DELETE FROM attraction_tag WHERE attraction_id = ?";
        jdbcTemplate.update(deleteTagsSql, updatedTouristAttraction.getAttractionId());

        saveTagsForAttraction(updatedTouristAttraction.getAttractionId(), updatedTouristAttraction.getTags());
    }

    // SKAL MULIGVIS SLETTES FOR AT OVERHOLDE CRUD
    public void updateTouristAttractionName(TouristAttraction touristAttraction, String name) {
        String sql = "UPDATE tourist_attraction SET name = ? WHERE attraction_id = ?";
        jdbcTemplate.update(sql, name, touristAttraction.getAttractionId());
    }

    // SKAL MULIGVIS SLETTES FOR AT OVERHOLDE CRUD
    public void updateTouristAttractionDescription(TouristAttraction touristAttraction, String description) {
        String sql = "UPDATE tourist_attraction SET description = ? WHERE attraction_id = ?";
        jdbcTemplate.update(sql, description, touristAttraction.getAttractionId());
    }

    public void deleteByName(String name) {
        TouristAttraction attraction = getTouristAttractionByName(name);
        if (attraction == null) {
            return;
        }

        String deleteJoinSql = "DELETE FROM attraction_tag WHERE attraction_id = ?";
        jdbcTemplate.update(deleteJoinSql, attraction.getAttractionId());

        String deleteAttractionSql = "DELETE FROM tourist_attraction WHERE attraction_id = ?";
        jdbcTemplate.update(deleteAttractionSql, attraction.getAttractionId());
    }



}
