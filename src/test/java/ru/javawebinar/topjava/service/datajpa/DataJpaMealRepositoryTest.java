package ru.javawebinar.topjava.service.datajpa;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.MealServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaMealRepositoryTest extends MealServiceTest {

    @Test
    public void getMealWithUser() throws Exception {
        Meal meal = service.getMealWithUser(MEAL1_ID, USER_ID);
        User user = meal.getUser();
        MEAL_MATCHER.assertMatch(meal, MEAL1);
        USER_MATCHER.assertMatch(user, USER);
    }

    @Test
    public void getMealWithUserNotFound() throws Exception {
        Assert.assertThrows(NotFoundException.class,
                () -> service.getMealWithUser(MEAL1_ID, ADMIN_ID));
    }
}