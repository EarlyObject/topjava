package ru.javawebinar.topjava.DAO;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.util.List;

import static ru.javawebinar.topjava.web.MealServlet.listOfMeal;

public class MealDAO {

    public void addMeal(Meal meal) {
        listOfMeal.save(meal);
    }

    public void deleteMeal(Integer mealId) {
       listOfMeal.delete(mealId);
    }

    public void updateMeal(Meal meal) {
      listOfMeal.update(meal);
    }

    public List<MealTo> getAllMeal() {
        return listOfMeal.convertToMealTo();
    }

    public Meal getMealById(Integer mealId) {
        return listOfMeal.get(mealId);
    }
}
