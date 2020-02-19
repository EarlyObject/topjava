package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static java.time.LocalTime.MAX;
import static java.time.LocalTime.MIN;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public List<MealTo> getAllTo() {
        log.info("getAllTo");
        return (MealsUtil.getTos(service.getAll(authUserId()), SecurityUtil.authUserCaloriesPerDay()));
    }

    public Meal get(int mealId) {
        log.info("get {}", mealId);
        return service.get(mealId, authUserId());
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        checkNew(meal);
        return service.create(meal, authUserId());
    }

    public void delete(int mealId) {
        log.info("delete {}", mealId);
        service.delete(mealId, authUserId());
    }

    public void update(Meal meal, int mealId) {
        log.info("update {} with id={}", meal, mealId);
        assureIdConsistent(meal, mealId);
        service.update(meal, authUserId());
    }

    public List<MealTo> filter(LocalDate dateFrom, LocalDate dateTo, LocalTime timeFrom, LocalTime timeTo) {
        LocalDate startDate = dateFrom != null ? dateFrom : LocalDate.MIN;
        LocalDate endDate = dateTo != null ? dateTo : LocalDate.MAX;
        LocalTime startTime = timeFrom != null ? timeFrom : MIN;
        LocalTime endTime = timeTo != null ? timeTo : MAX;
        return MealsUtil.getFilteredTos(service.getAllFilteredByDate((authUserId()), startDate, endDate),
                SecurityUtil.authUserCaloriesPerDay(),
                startTime,
                endTime);
    }
}