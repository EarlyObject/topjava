package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class MealRestController extends AbstractMealController {

    public List<MealTo> getBetween(LocalDate startDate, LocalTime startTime,
                                   LocalDate endDate, LocalTime endTime) {
        return super.getBetween(startDate, startTime, endDate, endTime);
    }
}