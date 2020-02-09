package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.DAO.MealDAO;
import ru.javawebinar.topjava.model.Meal;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.slf4j.LoggerFactory.getLogger;

@WebServlet("/CrudServlet")
public class CrudServlet extends HttpServlet {
    private static final long serialVersionUID = 3284493691900302606L;
    private static final Logger log = getLogger(CrudServlet.class);
    private static String LIST_OF_MEAL = "meals.jsp";

    private MealDAO dao;

    public CrudServlet() {
        this.dao = new MealDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("CrudServlet doGet");

        String forward;
        String action = request.getParameter("action");

        String INSERT_OR_EDIT = "crud.jsp";
        if (action.equalsIgnoreCase("delete")) {
            Integer mealId = Integer.parseInt(request.getParameter("mealId"));
            dao.deleteMeal(mealId);
            forward = LIST_OF_MEAL;
            request.setAttribute("meals", dao.getAllMeal());
        } else if (action.equalsIgnoreCase("edit"))  {
            forward = INSERT_OR_EDIT;
            Integer mealId = Integer.parseInt(request.getParameter("mealId"));
            Meal meal = dao.getMealById(mealId);
            request.setAttribute("mealId", mealId);
            request.setAttribute("meal", meal);
        } else if (action.equalsIgnoreCase("listOfMeals")) {
            forward = LIST_OF_MEAL;
            request.setAttribute("meals", dao.getAllMeal());
        } else  {
            forward = INSERT_OR_EDIT;
        }

        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("CrudServlet doPost");

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            LocalDateTime localDateTime = LocalDateTime.parse(request.getParameter("date"), formatter);
            String description = request.getParameter("description");
            int calories = Integer.parseInt(request.getParameter("calories"));
            String mealId = request.getParameter("mealId");

            if (mealId == null) {
                Meal meal = new Meal(localDateTime, description, calories);
                dao.addMeal(meal);
            } else {
                Meal meal = dao.getMealById(Integer.parseInt(mealId));
                meal.setDateTime(localDateTime);
                meal.setCalories(calories);
                meal.setDescription(description);
                dao.updateMeal(meal);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        RequestDispatcher view = request.getRequestDispatcher(LIST_OF_MEAL);
        request.setAttribute("meals", dao.getAllMeal());
        view.forward(request, response);
    }
}
