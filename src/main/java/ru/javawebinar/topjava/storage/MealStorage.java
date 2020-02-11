package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealStorage {
    void save(Meal meal);

    Meal get(int mealId);

    void update(int mealId, Meal meal);

    void delete(Integer mealId);

    List<Meal> getAll();
}
