package ru.javawebinar.topjava.web.meal;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = MealAjaxController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MealAjaxController extends AbstractMealController {
    static final String REST_URL = "/ajax/profile/meals";

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealTo> getAll() {
        return super.getAll();
    }


    @PostMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void createOrUpdate(@RequestParam Integer id,
                               @RequestParam(name = "datetime") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime dateTime,
                               @RequestParam String  description,
                               @RequestParam int calories) {
        Meal meal = new Meal(id, dateTime,  description, calories);
        if (meal.isNew()) {
            super.create(meal);
        } else {
            super.update(meal, id);
        }
    }
}