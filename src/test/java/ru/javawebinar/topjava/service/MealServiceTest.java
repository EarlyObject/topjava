package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(100002, USER_ID);
        assertMatch(meal, MEAL_1);
    }

    @Test(expected = NotFoundException.class)
    public void get_others() {
        Meal meal = service.get(8, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void delete() throws Exception {
        service.delete(2, USER_ID);
        service.get(2, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void delete_others() throws Exception {
        service.delete(9, USER_ID);
    }

    @Test
    public void getBetweenHalfOpen() {
        List<Meal> list = service.getBetweenHalfOpen(LocalDate.of(2020, 1, 29), LocalDate.of(2020, 1, 30), USER_ID);
        List<Meal> expected = Arrays.asList(MEAL_4, MEAL_3, MEAL_2, MEAL_1);
        assertMatch(list, expected);
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(USER_ID);
        MealTestData.assertMatch(all, USER_MEAL_LIST);
    }

    @Test
    public void update() {
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
        assertMatch(service.get(100002, USER_ID), updated);
    }

    @Test(expected = NotFoundException.class)
    public void update_others() {
        Meal updated = getUpdated();
        service.update(updated, ADMIN_ID);
    }

    @Test
    public void create() {
        Meal newMeal = getNew();
        Meal created = service.create(newMeal, USER_ID);
        Integer newId = created.getId();
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, USER_ID), newMeal);
    }
}