package com.karkia.todo.repository;

import com.karkia.todo.domain.ToDo;
import lombok.val;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is using NamedParameterJdbcTemplate (a JdbcTemplate wrapper) that helps with all the named parameters,
 * which means that instead of using ? in your SQL statements, you use names like :id
 */
@Repository
public class ToDoRepository implements CommonRepository<ToDo> {

    private static final String SQL_INSERT = "insert into todo (id, description, created, modified, completed) " +
            "values (:id,:description, :created,:modified,:completed)";
    private static final String SQL_QUERY_FIND_ALL = "select id, description, created, modified, completed from todo";
    private static final String SQL_QUERY_FIND_BY_ID = SQL_QUERY_FIND_ALL + " where id = :id";
    private static final String SQL_UPDATE = "update todo set description = :description, modified = :modified, " +
            "completed = :completed where id = :id";
    private static final String SQL_DELETE = "delete from todo where id = :id";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private Map<String, ToDo> toDos = new HashMap<>();

    public ToDoRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // JdbcTemplate used the RowMapper for mapping rows of a ResultSet on a per-row basis.
    private RowMapper<ToDo> toDoRowMapper = (ResultSet rs, int rowNum) -> {
        val toDo = new ToDo();
        toDo.setId(rs.getString("id"));
        toDo.setDescription(rs.getString("description"));
        toDo.setModified(rs.getTimestamp("modified").toLocalDateTime());
        toDo.setCreated(rs.getTimestamp("created").toLocalDateTime());
        toDo.setCompleted(rs.getBoolean("completed"));

        return toDo;
    };

    @Override
    public ToDo save(final ToDo domain) {
        ToDo result = findById(domain.getId());

        if (result != null) {
            result.setDescription(domain.getDescription());
            result.setCompleted(domain.isCompleted());
            result.setModified(domain.getModified());

            return upsert(result, SQL_UPDATE);
        }

        // if the object doesn't yet exist, just create a new object
        return upsert(domain, SQL_INSERT);
    }

    private ToDo upsert(final ToDo toDo, final String sql) {
        val namedParameters = new HashMap<String, Object>();
        namedParameters.put("id", toDo.getId());
        namedParameters.put("description", toDo.getDescription());
        namedParameters.put("created", Timestamp.valueOf(toDo.getCreated()));
        namedParameters.put("modified", Timestamp.valueOf(toDo.getModified()));
        namedParameters.put("completed", toDo.isCompleted());

        this.jdbcTemplate.update(sql, namedParameters);

        return findById(toDo.getId());
    }

    @Override
    public Iterable<ToDo> save(Collection<ToDo> domains) {
        domains.forEach(this::save);
        return findAll();
    }

    @Override
    public void delete(final ToDo domain) {
        val namedParameters = Collections.singletonMap("id", domain.getId());
        this.jdbcTemplate.update(SQL_DELETE, namedParameters);
    }

    @Override
    public ToDo findById(String id) {
        try {
            val namedParameters = Collections.singletonMap("id", id);
            return this.jdbcTemplate.queryForObject(SQL_QUERY_FIND_BY_ID, namedParameters, toDoRowMapper);
        } catch (EmptyResultDataAccessException erdae) {
            return null;
        }
    }

    @Override
    public Iterable<ToDo> findAll() {
        return this.jdbcTemplate.query(SQL_QUERY_FIND_ALL, toDoRowMapper);
    }
}
