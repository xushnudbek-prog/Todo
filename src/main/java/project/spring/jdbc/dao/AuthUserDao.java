package project.spring.jdbc.dao;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;
import project.spring.jdbc.domains.AuthUser;
import project.spring.jdbc.utils.AuthUserRowMapper;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthUserDao {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public int save(@NonNull AuthUser authUser) {
        String sql = "insert into authuser(username, password, upload_id) values(:username, :password, :upload_id)";
        var params = new MapSqlParameterSource()
                .addValue("username", authUser.getUsername())
                .addValue("upload_id", authUser.getProfilePictureId())
                .addValue("password", authUser.getPassword());
        var keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, params, keyHolder, new String[]{"id"});
        int id = Objects.requireNonNull(keyHolder.getKey()).intValue();
        setRole(id, 3);
        return id;
    }

    public Optional<AuthUser> findByUsername(@NonNull String username) {
        var sql = "select * from authuser where username = :username";
        var paramSource = new MapSqlParameterSource().addValue("username", username);
        AuthUserRowMapper authUserRowMapper = new AuthUserRowMapper();
        try {
            var authUser = namedParameterJdbcTemplate.queryForObject(sql, paramSource, authUserRowMapper);
            return Optional.of(authUser);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public List<AuthUser> getAllSimpleUsers() {
        var sql = "select * from authuser au inner join authuser_authrole auar on au.id = auar.user_id inner join authrole ar on auar.role_id = ar.id where ar.code = 'USER';";
        return namedParameterJdbcTemplate.query(sql, new AuthUserRowMapper());
    }

    public void blockUser(int userId) {
        var sql = "update authuser set is_blocked = true where id = :userId;";
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource().addValue("userId", userId));
    }

    public void unblockUser(int userId) {
        var sql = "update authuser set is_blocked = false where id = :userId;";
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource().addValue("userId", userId));
    }

    public void setRole(int userId, int roleId) {
        var sql = "insert into authuser_authrole(user_id, role_id) values(:userId, :roleId)";
        var params = new MapSqlParameterSource()
                .addValue("userId", userId)
                .addValue("roleId", roleId);
        namedParameterJdbcTemplate.update(sql, params);
    }
}
