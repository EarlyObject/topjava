package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;


@Controller
@RequestMapping("/meals")
public class JspMealController {
    private static final Logger log = LoggerFactory.getLogger(JspMealController.class);

    @Autowired
    private MealService service;

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String deleteMeal(@PathVariable("id") int id) {
        service.delete(id, SecurityUtil.authUserId());
        return "redirect:/meals";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String setMeal(Integer id, HttpServletRequest request) {

        Optional<Integer> optionalId = Optional.ofNullable(id);
        Meal meal;
        meal = optionalId.isEmpty() ? new Meal() : service.get(optionalId.get(), SecurityUtil.authUserId());

        meal.setDateTime(LocalDateTime.parse(request.getParameter("dateTime")));
        meal.setDescription(request.getParameter("description"));
        meal.setCalories(Integer.parseInt(request.getParameter("calories")));

        if (optionalId.isEmpty()) {
            service.create(meal, SecurityUtil.authUserId());
        } else {
            service.update(meal, SecurityUtil.authUserId());
        }

        return "redirect:/meals";
    }


    @GetMapping
    public String getMeals(Model model, HttpServletRequest request) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");

        Enumeration enumeration = request.getParameterNames();
        Map<String, Object> modelMap = new HashMap<>();
        while (enumeration.hasMoreElements()) {
            String parameterName = (String) enumeration.nextElement();
            modelMap.put(parameterName, request.getParameter(parameterName));
        }

        String action = request.getParameter("action");
        switch (action == null ? "all" : action) {
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        service.get(getId(request), SecurityUtil.authUserId());
                request.setAttribute("meal", meal);
                return "mealForm";
            case "filter":
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");

                LocalDate startDate = request.getParameter("startDate").equals("") ? LocalDate.of(1900, 1, 1) : LocalDate.parse(request.getParameter("startDate"), formatter);
                LocalDate endDate = request.getParameter("endDate").equals("") ? LocalDate.of(3000, 1, 1) : LocalDate.parse(request.getParameter("endDate"), formatter);
                LocalTime startTime = request.getParameter("startTime").equals("") ? LocalTime.of(00, 00, 00) : LocalTime.parse(request.getParameter("startTime"), dtf);
                LocalTime endTime = request.getParameter("endTime").equals("") ? LocalTime.of(23, 59, 59) : LocalTime.parse(request.getParameter("endTime"), dtf);
                model.addAttribute("meals", getBetween(startDate, startTime, endDate, endTime));
                break;
            case "all":
            default:
                model.addAttribute("meals", MealsUtil.getTos(service.getAll(SecurityUtil.authUserId()), MealsUtil.DEFAULT_CALORIES_PER_DAY));

                return "meals";
        }
        return "meals";
    }


    public List<MealTo> getBetween(@Nullable LocalDate startDate, @Nullable LocalTime startTime,
                                   @Nullable LocalDate endDate, @Nullable LocalTime endTime) {
        int userId = SecurityUtil.authUserId();
        log.info("getBetween dates({} - {}) time({} - {}) for user {}", startDate, endDate, startTime, endTime, userId);

        List<Meal> mealsDateFiltered = service.getBetweenInclusive(startDate, endDate, userId);
        return MealsUtil.getFilteredTos(mealsDateFiltered, SecurityUtil.authUserCaloriesPerDay(), startTime, endTime);
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}

