package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ListStorage implements Storage {
    protected final List<Meal> storage = new ArrayList<>();

    public ListStorage() {
    }

    @Override
    public void save(Meal meal) {
        storage.add(meal);
    }

    @Override
    public Meal get(Integer mealId) {
        Integer searchKey = getSearchKey(mealId);
        return storage.get(searchKey);
    }

    @Override
    public void update(Meal meal) {
        Integer searchKey = getSearchKey(meal.getId());
        storage.set(searchKey, meal);
    }

    @Override
    public void delete(Integer mealId) {
        Integer searchKey = getSearchKey(mealId);
        storage.remove(searchKey.intValue());
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public List<Meal> getAll() {
        return  new ArrayList<>(storage);
    }

    public List<MealTo> convertToMealTo() {
    //    List<Meal> meals = new ArrayList<>(storage);
        List<MealTo> returnValue = MealsUtil.filteredByStreams(storage, LocalTime.MIN, LocalTime.MAX, 2000);
        return returnValue;
    }

    public void addAll(List<Meal> list) {
        storage.addAll(list);
    }

    protected Integer getSearchKey(Integer mealId) {
        int storageSize = storage.size();
        for (int i = 0; i < storageSize; i++) {
            if (mealId.equals(storage.get(i).getId())) {
                return i;
            }
        }
        return null;
    }
}
