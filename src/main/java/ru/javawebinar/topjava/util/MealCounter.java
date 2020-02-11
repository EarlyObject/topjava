package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

public class MealCounter {
    private static AtomicInteger counter = new AtomicInteger();

    public static Meal getMealObject(LocalDateTime dateTime, String description, int calories) {
        counter.incrementAndGet();
        Meal meal = new Meal(dateTime, description, calories);
        meal.setId(counter.intValue());
        return meal;
    }

    public AtomicInteger getCounter() {
        return counter;
    }

    public void setCounter(AtomicInteger counter) {
        this.counter = counter;
    }
}
