package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository("inMemoryMealRepository")
public class InMemoryMealRepository implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
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
            repository.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        int mealId = meal.getId();
        if (!repository.containsKey(mealId)) {
            throw new NotFoundException("MEAL NOT FOUND");
        } else if (repository.get(mealId).getUserId() != userId) {
            throw new NotFoundException("WRONG USER ID");
        }
        meal.setUserId(userId);
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        if (!repository.containsKey(id)) {
            throw new NotFoundException("MEAL NOT FOUND");
        } else if (repository.get(id).getUserId() != userId) {
            throw new NotFoundException("WRONG USER ID");
        }
        return repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        if (!repository.containsKey(id)) {
            throw new NotFoundException("MEAL NOT FOUND");
        } else if (repository.get(id).getUserId() != userId) {
            throw new NotFoundException("WRONG USER ID");
        }
        return repository.get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return repository.values()
                .stream()
                .filter(n -> n.getUserId() == userId)
                .sorted((n1, n2) -> n2.getDateTime()
                        .compareTo(n1.getDateTime()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Meal> filter(int userId, String dFrom, String dTo, String tFrom, String tTo) {
        LocalDate start;
        LocalDate end;
        LocalTime tStart;
        LocalTime tEnd;

        List<Meal> returnValue = getAll(userId);
        List<Meal> tempList;

        if (!dFrom.equals("") && !dTo.equals("")) {
            start = LocalDate.parse(dFrom, DateTimeUtil.DATEFORMATTER);
            end = LocalDate.parse(dTo, DateTimeUtil.DATEFORMATTER);
            tempList = returnValue
                    .stream()
                    .filter(n -> DateTimeUtil.isBetween(n.getDate(), start, end))
                    .collect(Collectors.toList());
            returnValue = tempList;
        } else if (!dFrom.equals("")) {
            start = LocalDate.parse(dFrom, DateTimeUtil.DATEFORMATTER);
            tempList = returnValue
                    .stream()
                    .filter(n -> DateTimeUtil.isBetween(n.getDate(), start, LocalDate.MAX))
                    .collect(Collectors.toList());
            returnValue = tempList;
        } else if (!dTo.equals("")) {
            end = LocalDate.parse(dTo, DateTimeUtil.DATEFORMATTER);
            tempList = returnValue
                    .stream()
                    .filter(n -> DateTimeUtil.isBetween(n.getDate(), LocalDate.MIN, end))
                    .collect(Collectors.toList());
            returnValue = tempList;
        }

        if (!tFrom.equals("") && !tTo.equals("")) {
            tStart = LocalTime.parse(tFrom);
            tEnd = LocalTime.parse(tTo);
            tempList = returnValue
                    .stream()
                    .filter(n -> DateTimeUtil.isBetween(n.getTime(), tStart, tEnd))
                    .collect(Collectors.toList());
            returnValue = tempList;
        } else if (!tFrom.equals("")) {
            tStart = LocalTime.parse(tFrom);
            tempList = returnValue
                    .stream()
                    .filter(n -> DateTimeUtil.isBetween(n.getTime(), tStart, LocalTime.MAX))
                    .collect(Collectors.toList());
            returnValue = tempList;
        } else if (!tTo.equals("")) {
            tEnd = LocalTime.parse(tTo);
            tempList = returnValue
                    .stream()
                    .filter(n -> DateTimeUtil.isBetween(n.getTime(), LocalTime.MIN, tEnd))
                    .collect(Collectors.toList());
            returnValue = tempList;
        }

        return returnValue;
    }
}

