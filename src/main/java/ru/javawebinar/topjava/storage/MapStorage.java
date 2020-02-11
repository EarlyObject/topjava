package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapStorage implements Storage {
    protected final Map<Integer, Meal> storage = new ConcurrentHashMap<>();

    public MapStorage() {
    }

    @Override
    public void save(Meal meal) {
        storage.put(meal.getId(), meal);
    }

    @Override
    public Meal get(Integer mealId) {
        return storage.get(mealId);
    }

    @Override
    public void update(Meal meal) {
        storage.replace(meal.getId(), meal);
    }

    @Override
    public void delete(Meal meal) {
        storage.remove(meal.getId());
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(storage.values());
    }
}
