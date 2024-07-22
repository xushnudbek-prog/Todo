package project.spring.jdbc.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import project.spring.jdbc.dao.ProfilePictureDao;
import project.spring.jdbc.domains.AuthUser;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class AuthUserRowMapper implements RowMapper<AuthUser> {
    @Override
    public AuthUser mapRow(ResultSet rs, int rowNum) throws SQLException {
        return AuthUser.builder()
                .id(rs.getInt("id"))
                .username(rs.getString("username"))
                .password(rs.getString("password"))
                .isBlocked(rs.getBoolean("is_blocked"))
                .profilePictureId(rs.getInt("upload_id"))
                .build();
    }
}
