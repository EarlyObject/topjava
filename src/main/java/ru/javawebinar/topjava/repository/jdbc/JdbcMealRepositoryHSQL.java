package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
@Profile("hsqldb")
public class JdbcMealRepositoryHSQL extends JdbcMealRepository {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public JdbcMealRepositoryHSQL(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    protected List<Meal> getBetweenHalfOpenImpl(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId, JdbcTemplate jdbcTemplate, RowMapper<Meal> rowMapper) {
        return jdbcTemplate.query(
                "SELECT * FROM meals WHERE user_id=? AND date_time >= ? AND  date_time < ? ORDER BY date_time DESC",
                rowMapper, userId, startDateTime.format(DATE_TIME_FORMATTER), endDateTime.format(DATE_TIME_FORMATTER));
    }

    @Override
    protected Meal saveImpl(Meal meal, int userId, NamedParameterJdbcTemplate namedParameterJdbcTemplate, SimpleJdbcInsert insertMeal) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", meal.getId())
                .addValue("description", meal.getDescription())
                .addValue("calories", meal.getCalories())
                .addValue("date_time", meal.getDateTime().format(DATE_TIME_FORMATTER))
                .addValue("user_id", userId);

        if (meal.isNew()) {
            Number newId = insertMeal.executeAndReturnKey(map);
            meal.setId(newId.intValue());
        } else {
            if (namedParameterJdbcTemplate.update("" +
                            "UPDATE meals " +
                            "   SET description=:description, calories=:calories, date_time=:date_time " +
                            " WHERE id=:id AND user_id=:user_id"
                    , map) == 0) {
                return null;
            }
        }
        return meal;
    }
}
