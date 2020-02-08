package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class Meal {
    private static volatile Integer counter = 0;

    private final LocalDateTime dateTime;
    private final String description;
    private final int calories;
    private final Integer id;


    public Meal(LocalDateTime dateTime, String description, int calories) {
        this.dateTime = Objects.requireNonNull(dateTime);
        this.description = Objects.requireNonNull(description);
        this.calories = Objects.requireNonNull(calories);
        counter += 1;
        this.id = counter;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public Integer getId() {
        return id;
    }
}
