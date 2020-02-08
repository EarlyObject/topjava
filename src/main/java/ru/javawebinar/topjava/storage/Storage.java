package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface Storage {
    void save(Meal meal);

    Meal get(Integer mealId);

    void update(Meal meal);

    void delete(Integer mealId);

    int size();

    void clear();

    List<Meal> getAll();
}
