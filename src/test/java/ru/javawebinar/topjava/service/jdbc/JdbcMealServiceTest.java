package ru.javawebinar.topjava.service.jdbc;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;

import static ru.javawebinar.topjava.MealTestData.MEAL1_ID;
import static ru.javawebinar.topjava.Profiles.JDBC;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ActiveProfiles(JDBC)
public class JdbcMealServiceTest extends AbstractMealServiceTest {

    @Test(expected = IllegalArgumentException.class)
    public void delete() throws Exception {
        service.delete(MEAL1_ID, USER_ID);
        Assert.assertNull(repository.get(MEAL1_ID, USER_ID));
    }

    @Test
    public void getNotFound() throws Exception {
        Assert.assertThrows(IllegalArgumentException.class,
                () -> service.get(MEAL1_ID, ADMIN_ID));
    }

    @Test
    public void getNotOwn() throws Exception {
        Assert.assertThrows(IllegalArgumentException.class,
                () -> service.get(MEAL1_ID, ADMIN_ID));
    }
}