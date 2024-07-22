package project.spring.jdbc.dao;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;
import project.spring.jdbc.domains.AuthRole;
import project.spring.jdbc.domains.AuthUser;
import project.spring.jdbc.utils.AuthUserRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthRoleDao {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public List<AuthRole> findAllByUserId(int userId) {
        var sql = "select * from authuser_authrole auar inner join authrole ar on ar.id = auar.role_id where auar.user_id = :userId" ;
        var paramSource = new MapSqlParameterSource().addValue("userId", userId);
        try {
            return namedParameterJdbcTemplate.query(sql, paramSource, (rs, rowNum) -> AuthRole.builder()
                    .id(rs.getInt("id"))
                    .name(rs.getString("name"))
                    .code(rs.getString("code"))
                    .build());
        }catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
