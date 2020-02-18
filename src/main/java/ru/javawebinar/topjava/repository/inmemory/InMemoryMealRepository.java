package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository("inMemoryMealRepository")
public class InMemoryMealRepository implements MealRepository {
    private Map<Integer, ConcurrentHashMap<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> save(meal, 1));
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед User2", 500), 2);
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин User2", 410), 2);
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            if (!repository.containsKey(userId)) {
                repository.put(userId, new ConcurrentHashMap<>());
            }
            return repository.get(userId).put(meal.getId(), meal);
        }
        // handle case: update, but not present in storage
        int mealId = meal.getId();
        meal.setUserId(userId);
        return repository.get(userId).computeIfPresent(mealId, (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        return repository.get(userId).remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        return repository.get(userId).getOrDefault(id, null);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return repository.get(userId).values()
                .stream()
                .sorted((n1, n2) -> n2.getDateTime()
                        .compareTo(n1.getDateTime()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Meal> getAllFilteredByDate(int userId, LocalDate startDate, LocalDate endDate) {
        return getAll(userId)
                .stream()
                .filter(n -> DateTimeUtil.isBetween(n.getDate(), startDate, endDate))
                .collect(Collectors.toList());
    }
}

