package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.storage.MapStorage;
import ru.javawebinar.topjava.util.MealCounter;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final long serialVersionUID = -3757980620300353646L;
    private static final Logger log = getLogger(MealServlet.class);
    private static MapStorage storage = new MapStorage();

    public void init() throws ServletException {
    }

    static {
        storage.save(MealCounter.getMealObject(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        storage.save(MealCounter.getMealObject(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        storage.save(MealCounter.getMealObject(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        storage.save(MealCounter.getMealObject(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        storage.save(MealCounter.getMealObject(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        storage.save(MealCounter.getMealObject(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        storage.save(MealCounter.getMealObject(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        log.debug("MealServlet doGet");

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        List<Meal> meals = storage.getAll();
        List<MealTo> listToDisplay = MealsUtil.filteredByStreams(meals, LocalTime.MIN, LocalTime.MAX, 2000);

        String forward;
        String action = request.getParameter("action");
        String insertOrEdit = "meals.jsp";

        String listOfMealView = "meals.jsp";
        switch (action == null ? "" : action) {
            case "list":
                request.setAttribute("meals", listToDisplay);
            case "delete":
                Integer mealId = Integer.parseInt(request.getParameter("mealId"));
                Meal meal = storage.get(mealId);
                storage.delete(meal);
                response.sendRedirect("meals");
                return;
            case "edit":
                forward = insertOrEdit;
                mealId = Integer.parseInt(request.getParameter("mealId"));
                meal = storage.get(mealId);
                storage.delete(meal);
                request.setAttribute("mealId", mealId);
                request.setAttribute("meal", meal);
                break;
            case "insert":
                forward = insertOrEdit;
                mealId = null;
                request.setAttribute("mealId", mealId);
                break;
            default:
                forward = listOfMealView;
                request.setAttribute("meals", listToDisplay);
        }

        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.debug("MealServlet doPost");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        LocalDateTime localDateTime = LocalDateTime.parse(request.getParameter("date"), formatter);
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        String mealId = request.getParameter("mealId");

        if (mealId == null) {
            Meal meal = MealCounter.getMealObject(localDateTime, description, calories);
            storage.save(meal);
        } else {
            Meal meal = storage.get(Integer.parseInt(mealId));
            meal.setDateTime(localDateTime);
            meal.setCalories(calories);
            meal.setDescription(description);
            storage.update(meal);
        }

        response.sendRedirect("meals");
    }
}