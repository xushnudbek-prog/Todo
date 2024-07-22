package project.spring.jdbc.dao;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import project.spring.jdbc.domains.ProfilePicture;
import project.spring.jdbc.utils.ProfilePictureRowMapper;

import java.util.Map;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ProfilePictureDao {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    public int save(@NonNull ProfilePicture profilePicture) {
        var sql = "insert into uploads(original_name, generated_name, mimetype) values(:original_name, :generated_name, :mimetype) returning id";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("original_name", profilePicture.getOriginalName());
        mapSqlParameterSource.addValue("generated_name", profilePicture.getGeneratedName());
        mapSqlParameterSource.addValue("mimetype", profilePicture.getMimeType());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, mapSqlParameterSource, keyHolder, new String[]{"id"});
        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }
    public ProfilePicture getById(int id) {
        var sql = "select * from uploads where id = :id";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource("id", id);
        return namedParameterJdbcTemplate.queryForObject(sql, mapSqlParameterSource, new ProfilePictureRowMapper());
    }
}
