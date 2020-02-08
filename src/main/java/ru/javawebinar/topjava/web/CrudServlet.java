package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.web.MealServlet.listOfMeal;

@WebServlet("/crud")
public class CrudServlet extends HttpServlet {
    private static final long serialVersionUID = 3284493691900302606L;
    private static final Logger log = getLogger(CrudServlet.class);

    private static String INSERT_OR_EDIT = "crud.jsp";
    private static String LIST_OF_MEAL = "meals.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward="";
        String action = request.getParameter("action");

        if (action.equalsIgnoreCase("delete")){
            Integer mealId = Integer.parseInt(request.getParameter("mealId"));
            listOfMeal.delete(mealId);
            forward = LIST_OF_MEAL;
            request.setAttribute("meals", listOfMeal.convertToMealTo());
        } else if (action.equalsIgnoreCase("edit")){
            forward = INSERT_OR_EDIT;
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            Meal meal = listOfMeal.get(mealId);
            request.setAttribute("meal", meal);
        } else if (action.equalsIgnoreCase("listOfMeals")){
            forward = LIST_OF_MEAL;
            request.setAttribute("meals", listOfMeal.getAll());
        } else {
            forward = INSERT_OR_EDIT;
        }

        //RequestDispatcher view = request.getRequestDispatcher(forward);
        //view.forward(request, response);


        //request.setAttribute("meals", listToDisplay);
        request.getRequestDispatcher("meals.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //!!!! здесь будет ошибка ..pattern="yyyy-MM-dd HH:mm"
        try {
        LocalDateTime localDateTime = LocalDateTime.parse(request.getParameter("date"));
        String description = request.getParameter("description");
        Integer calories = Integer.parseInt(request.getParameter("calories"));
        Meal meal = new Meal(localDateTime, description, calories);
            listOfMeal.save(meal);

        } catch (Exception e) {
            e.printStackTrace();
        }

        RequestDispatcher view = request.getRequestDispatcher(LIST_OF_MEAL);
        request.setAttribute("meals", listOfMeal.getAll());
        view.forward(request, response);
    }
}
