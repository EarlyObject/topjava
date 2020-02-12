package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.storage.MealStorage;
import ru.javawebinar.topjava.storage.MealStorageMap;
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
    private MealStorage mealStorage;

    public void init() throws ServletException {
        mealStorage = new MealStorageMap();
        mealStorage.save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        mealStorage.save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        mealStorage.save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        mealStorage.save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        mealStorage.save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        mealStorage.save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        mealStorage.save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        log.debug("MealServlet doGet");

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        List<Meal> meals = mealStorage.getAll();
        List<MealTo> listToDisplay = MealsUtil.filteredByStreams(meals, LocalTime.MIN, LocalTime.MAX, 2000);

        String action = request.getParameter("action");
        String forward;
        String listOfMealView = "meals.jsp";
        String insertOrEdit = "saveOrUpdate.jsp";

        switch (action == null ? "" : action) {

            case "delete":
                int mealId = Integer.parseInt(request.getParameter("mealId"));
                mealStorage.delete(mealId);
                response.sendRedirect("meals");
                return;
            case "edit":
                forward = insertOrEdit;
                mealId = Integer.parseInt(request.getParameter("mealId"));
                Meal meal = mealStorage.get(mealId);
                request.setAttribute("meal", meal);
                request.setAttribute("mealId", mealId);
                break;
            case "insert":
                forward = insertOrEdit;
                break;
            default:
                forward = listOfMealView;
                request.setAttribute("meals", listToDisplay);
        }

        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.debug("MealServlet doPost");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        LocalDateTime localDateTime = LocalDateTime.parse(request.getParameter("date"), formatter);
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        String mealId = request.getParameter("mealId");

        Meal meal = new Meal(localDateTime, description, calories);
        if (mealId == "") {
            mealStorage.save(meal);
        } else {
            mealStorage.update(Integer.parseInt(mealId), meal);
        }
        response.sendRedirect("meals");
    }
}