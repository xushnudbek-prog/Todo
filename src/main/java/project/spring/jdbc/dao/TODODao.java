package project.spring.jdbc.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import project.spring.jdbc.domains.TODO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TODODao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insert;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public void save(TODO TODO) {

        String query = "insert into todo_list (title, priority, created_by) values (?, ?, ?);";
        jdbcTemplate.update(query, TODO.getTitle(), TODO.getPriority(), TODO.getCreatedBy());
    }
    public int simpleInsert(TODO TODO) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("title", TODO.getTitle());
        params.put("priority", TODO.getPriority());
        return insert.executeAndReturnKey(params).intValue();
    }
    public List<TODO> getList(int userId) {
        String query = "select * from todo_list where created_by = :createdBy;";
        BeanPropertyRowMapper<TODO> mapper = BeanPropertyRowMapper.newInstance(TODO.class);
        Map<String, Integer> params = Map.of("createdBy", userId);
        return namedParameterJdbcTemplate.query(query, params, mapper);
    }
    public void update(TODO TODO) {
        String query = "update todo_list set title = ?, priority = ? where id = ?;";
        jdbcTemplate.update(query, TODO.getTitle(), TODO.getPriority(), TODO.getId());
    }
    public TODO getById(int id) {
        String query = "select * from todo_list where id = ?;";
        BeanPropertyRowMapper<TODO> mapper = BeanPropertyRowMapper.newInstance(TODO.class);
        return jdbcTemplate.queryForObject(query, mapper, id);
    }
    public void delete(int id) {
        String query = "delete from todo_list where id = ?;";
        jdbcTemplate.update(query, id);
    }

}
