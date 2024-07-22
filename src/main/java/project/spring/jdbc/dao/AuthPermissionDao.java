package project.spring.jdbc.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import project.spring.jdbc.domains.AuthPermission;
import project.spring.jdbc.domains.AuthRole;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthPermissionDao {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public List<AuthPermission> findAllByRoleId(int roleId) {
        var sql = "select ap.* from authrole_authpermission arap inner join authpermission ap on ap.id = arap.permission_id where arap.role_id = :roleId;" ;
        var paramSource = new MapSqlParameterSource().addValue("roleId", roleId);
        try {
            return namedParameterJdbcTemplate.query(sql, paramSource, (rs, rowNum) -> AuthPermission.builder()
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
