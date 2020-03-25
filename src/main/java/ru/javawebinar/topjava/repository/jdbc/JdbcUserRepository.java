package ru.javawebinar.topjava.repository.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import javax.validation.ValidationException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static ru.javawebinar.topjava.util.JdbcValidator.validate;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepository implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(JdbcUserRepository.class);
    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    @Transactional
    public User save(User user) throws ValidationException {
        validate(user);
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);

        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
        } else if (namedParameterJdbcTemplate.update(
                "UPDATE users SET name=:name, email=:email, password=:password, " +
                        "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id", parameterSource) == 0) {
            return null;
        }
        jdbcTemplate.update("DELETE FROM user_roles WHERE user_id=?", user.getId());
        batchUpdate(new ArrayList<>(user.getRoles()), user.getId());

        return user;
    }

    public int[] batchUpdate(List<Role> roles, int userId) {

        return this.jdbcTemplate.batchUpdate(
                "UPDATE user_roles SET role=? WHERE user_id=?",
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, roles.get(i).toString());
                        ps.setInt(2, userId);
                    }
                    public int getBatchSize() {
                        return roles.size();
                    }
                });
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE  users.id =?", id) != 0;
    }

    @Override
    public User get(int id) {
        return setRoles(DataAccessUtils.singleResult(jdbcTemplate.query("SELECT * FROM users where id = ?", ROW_MAPPER, id)));
    }

    @Override
    public User getByEmail(String email) {
        return setRoles(DataAccessUtils.singleResult(jdbcTemplate.query("SELECT * FROM users WHERE email=?", ROW_MAPPER, email)));
    }

    @Override
    public List<User> getAll() {
        List<User> users = jdbcTemplate.query("SELECT * FROM users ORDER BY name, email", ROW_MAPPER);
        Map<Integer, Set<Role>> rolesMap = jdbcTemplate.query("SELECT * FROM user_roles", new RoleMapExtractor());
        for (User user : users) {
            user.setRoles(rolesMap.get(user.getId()));
        }
        return users;
    }

    private User setRoles(User user) {
        if (user != null) {
            user.setRoles(jdbcTemplate.query("SELECT * FROM user_roles WHERE user_id = ?",
                    (rs, rowNum) -> Role.valueOf(rs.getString("role")),
                    user.getId()));
        }
        return user;
    }

    private static final class RoleMapExtractor implements ResultSetExtractor<Map<Integer, Set<Role>>> {
        @Override
        public Map<Integer, Set<Role>> extractData(ResultSet rs) throws SQLException {
            Map<Integer, Set<Role>> rolesMap = new HashMap<>();
            while (rs.next()) {
                Integer id = Integer.parseInt(rs.getString("user_id"));
                Role role = Role.valueOf(rs.getString("role"));
                Set<Role> roles = rolesMap.get(id);
                if (roles == null) {
                    Set<Role> newRoles = EnumSet.noneOf(Role.class);
                    newRoles.add(role);
                    rolesMap.put(id, newRoles);
                } else {
                    roles.add(role);
                }
            }
            return rolesMap;
        }
    }
}
