package project.spring.jdbc.utils;

import org.springframework.jdbc.core.RowMapper;
import project.spring.jdbc.domains.ProfilePicture;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfilePictureRowMapper implements RowMapper<ProfilePicture> {
    @Override
    public ProfilePicture mapRow(ResultSet rs, int rowNum) throws SQLException {
        return ProfilePicture.builder()
                .id(rs.getInt("id"))
                .originalName(rs.getString("original_name"))
                .generatedName(rs.getString("generated_name"))
                .mimeType(rs.getString("mimetype"))
                .build();
    }
}
