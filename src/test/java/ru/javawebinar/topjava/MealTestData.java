package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {

    public static final Meal MEAL_1 = new Meal(100002, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
    public static final Meal MEAL_2 = new Meal(100003, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
    public static final Meal MEAL_3 = new Meal(100004, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);
    public static final Meal MEAL_4 = new Meal(100005, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100);
    public static final Meal MEAL_5 = new Meal(100006, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000);
    public static final Meal MEAL_6 = new Meal(100007, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500);
    public static final Meal MEAL_7 = new Meal(100008, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410);
    public static final Meal MEAL_8 = new Meal(100009, LocalDateTime.of(2015, Month.JUNE, 1, 14, 0), "Админ ланч", 510);
    public static final Meal MEAL_9 = new Meal(100010, LocalDateTime.of(2015, Month.JUNE, 1, 21, 0), "Админ ужин", 1500);

    public static final List<Meal> USER_MEAL_LIST = Arrays.asList(MEAL_7, MEAL_6, MEAL_5, MEAL_4, MEAL_3, MEAL_2, MEAL_1);

    public static Meal getNew() {
        return new Meal(LocalDateTime.of(2017, Month.JANUARY, 1, 12, 0), "TEST MEAL", 999);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(MEAL_1);
        updated.setId(100002);
        updated.setDescription("UpdatedDescription");
        updated.setCalories(555);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields().isEqualTo(expected);
    }
}
