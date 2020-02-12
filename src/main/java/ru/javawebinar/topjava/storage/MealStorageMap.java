package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealStorageMap implements MealStorage {
    private final Map<Integer, Meal> storage = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger();

    @Override
    public void save(Meal meal) {
        meal.setId(getCounter());
        storage.put(meal.getId(), meal);
    }

    @Override
    public Meal get(int mealId) {
        return storage.get(mealId);
    }

    @Override
    public void update(Meal meal) {
        storage.replace(meal.getId(), meal);
    }

    @Override
    public void delete(Integer mealId) {
        storage.remove(mealId);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(storage.values());
    }

    private int getCounter() {
       return counter.incrementAndGet();
    }
}
