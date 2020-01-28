package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);
        System.out.println();

        List<UserMealWithExcess> mealsToByStream = filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsToByStream.forEach(System.out::println);
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        List<UserMealWithExcess> returnValue = new ArrayList<>();
        Map<LocalDate, Integer> consumedCaloriesMap;
        consumedCaloriesMap = new HashMap<>();

        for (UserMeal userMeal : meals) {
            consumedCaloriesMap.merge(userMeal.getDateTime().toLocalDate(), userMeal.getCalories(), Integer::sum);

            if (TimeUtil.isBetweenInclusive(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                returnValue.add(new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), false));
            }
        }

        for (UserMealWithExcess meal : returnValue) {
            if (consumedCaloriesMap.get(meal.getDateTime().toLocalDate()) > caloriesPerDay) {
                meal.setExcess(true);
            }
        }
        return returnValue;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> consumedCaloriesMap = new HashMap<>();

        meals.forEach(n -> consumedCaloriesMap.merge(n.getDateTime().toLocalDate(), n.getCalories(), Integer::sum));

        return meals.stream().filter(x -> TimeUtil.isBetweenInclusive(x.getDateTime().toLocalTime(), startTime, endTime))
                .map(n -> new UserMealWithExcess(n.getDateTime(), n.getDescription(), n.getCalories(),
                        consumedCaloriesMap.get(n.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }
}
