import DAO.Comment_DAO_SQL2O;
import DAO.Hike_DAO_SQL2O;
import DAO.Location_DAO_SQL2O;
import DAO.Review_DAO_SQL2O;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.*;
import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;
import static spark.Spark.*;

public class App {

    public static void main(String[] args) {
        staticFileLocation("/public");
        String connectionString = "jdbc:h2:~/todolist.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        Location_DAO_SQL2O locationDao = new Location_DAO_SQL2O(sql2o);
        Comment_DAO_SQL2O commentDao = new Comment_DAO_SQL2O(sql2o);
        Hike_DAO_SQL2O hikeDao = new Hike_DAO_SQL2O(sql2o);
        Review_DAO_SQL2O reviewDao = new Review_DAO_SQL2O(sql2o);


        //get a specific category (and the tasks it contains)
        //  /categories/:category_id
        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();

            List<Location> allLocations = locationDao.getAll();
            model.put("locations", allLocations);

            List<Hike> hikes = hikeDao.getAll();
            model.put("hikes", hikes);
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());


        //get: show a form to create a new category
        //  /categories/new
        get("/locations/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();

            List<Location> locations = locationDao.getAll(); //refresh list of links for navbar.
            model.put("locations", locations);

            return new ModelAndView(model, "location-form.hbs"); //new
        }, new HandlebarsTemplateEngine());


        //post: process a form to create a new category
        //  /categories
        post("/locations", (request, response) -> { //new
            Map<String, Object> model = new HashMap<>();
            String hike = request.queryParams("hike");
            Location newLocation = new Location(hike);
            locationDao.add(newLocation);

            List<Location> locations = locationDao.getAll(); //refresh list of links for navbar.
            model.put("locations", locations);

            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());

        //get: delete all categories and all tasks
        get("/locations/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            hikeDao.clearAllHikes();
            locationDao.clearAllLocations();

            List<Location> allLocations = locationDao.getAll();
            model.put("locations", allLocations);

            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());


        //get: show a form to update a category
        get("/locations/update", (req, res) -> {
            Map<String, Object> model = new HashMap<>();

            model.put("editLocation", true);

            List<Location> allLocations = locationDao.getAll();
            model.put("locations", allLocations);

            return new ModelAndView(model, "location-form.hbs");
        }, new HandlebarsTemplateEngine());

        //post: process a form to update a category
        //  /categories/update
        post("/locations/update", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfLocationToEdit = Integer.parseInt(req.queryParams("editLocationId"));
            String newHike = req.queryParams("newLocationName");
            locationDao.update(locationDao.findById(idOfLocationToEdit).getIdLocation(), newHike);

            List<Location> locations = locationDao.getAll(); //refresh list of links for navbar.
            model.put("locations", locations);

            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());

        //get: show a specific task in a specific category
        //  /categories/:category_id/tasks/:task_id
        get("/locations/:idLocations", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfLocationToFind = Integer.parseInt(req.params("idLocation")); //new

            List<Location> locations = locationDao.getAll(); //refresh list of links for navbar.
            model.put("locations", locations);

            Location foundLocation = locationDao.findById(idOfLocationToFind);
            model.put("location", foundLocation);
            List<Hike> allHikesByLocation = locationDao.getAllHikesByLocation(idOfLocationToFind);
            model.put("hikes", allHikesByLocation);

            return new ModelAndView(model, "location-detail.hbs"); //new
        }, new HandlebarsTemplateEngine());





//
//
//        //get: show all tasks in all categories and show all categories
//        get("/", (req, res) -> {
//            Map<String, Object> model = new HashMap<>();
//            List<Task> tasks = taskDao.getAll();
//            model.put("tasks", tasks);
//            return new ModelAndView(model, "index.hbs");
//        }, new HandlebarsTemplateEngine());
//
//        //get: delete all tasks
//        get("/tasks/delete", (req, res) -> {
//            Map<String, Object> model = new HashMap<>();
//
//            List<Category> allCategories = categoryDao.getAll();
//            model.put("categories", allCategories);
//
//            taskDao.clearAllTasks();
//            return new ModelAndView(model, "success.hbs");
//        }, new HandlebarsTemplateEngine());
//
//
//        //get: show new task form
//        get("/tasks/new", (req, res) -> {
//            Map<String, Object> model = new HashMap<>();
//
//            List<Category> allCategories = categoryDao.getAll();
//            model.put("categories", allCategories);
//
//            return new ModelAndView(model, "task-form.hbs");
//        }, new HandlebarsTemplateEngine());
//
//
//        //post: process new task form
//        post("/tasks/new", (request, response) -> {
//            Map<String, Object> model = new HashMap<>();
//
//            List<Category> allCategories = categoryDao.getAll();
//            model.put("categories", allCategories);
//
//            String taskName = request.queryParams("taskName");
//            String description = request.queryParams("description");
//            int categoryId = Integer.parseInt(request.queryParams("categoryId"));
//            Task newTask = new Task(taskName, description, categoryId ); //ignore the hardcoded categoryId
//            taskDao.add(newTask);
//            model.put("task", newTask);
//            return new ModelAndView(model, "success.hbs");
//        }, new HandlebarsTemplateEngine());
//
//
//
//        //get: show an individual task that is nested in a category
//        get("/categories/:category_id/tasks/:task_id", (req, res) -> {
//            Map<String, Object> model = new HashMap<>();
//            int idOfTaskToFind = Integer.parseInt(req.params("task_id"));
//            Task foundTask = taskDao.findById(idOfTaskToFind);
//            model.put("task", foundTask);
//            return new ModelAndView(model, "task-detail.hbs");
//        }, new HandlebarsTemplateEngine());
//
//        //get: show a form to update a task
//        get("/tasks/update", (req, res) -> {
//            Map<String, Object> model = new HashMap<>();
//
//            List<Category> allCategories = categoryDao.getAll();
//            model.put("categories", allCategories);
//
//            List<Task> allTasks = taskDao.getAll();
//            model.put("tasks", allTasks);
//
//            model.put("editTask", true);
//            return new ModelAndView(model, "task-form.hbs");
//        }, new HandlebarsTemplateEngine());
//
//        //post: process a form to update a task
//        post("/tasks/update", (req, res) -> {
//            Map<String, Object> model = new HashMap<>();
//
//            List<Category> allCategories = categoryDao.getAll();
//            model.put("categories", allCategories);
//
//            String newName = req.queryParams("taskName");
//            String newContent = req.queryParams("description");
//            int newCategoryId = Integer.parseInt(req.queryParams("categoryId"));
//            int taskToEditId = Integer.parseInt(req.queryParams("taskToEditId"));
//            Task editTask = taskDao.findById(taskToEditId);
//            taskDao.update(taskToEditId, newName, newContent, newCategoryId);
//
//            return new ModelAndView(model, "success.hbs");
//        }, new HandlebarsTemplateEngine());
//
//        //get: delete an individual task
//        get("categories/:category_id/tasks/:task_id/delete", (req, res) -> {
//            Map<String, Object> model = new HashMap<>();
//            int idOfTaskToDelete = Integer.parseInt(req.params("task_id"));
//            Task deleteTask = taskDao.findById(idOfTaskToDelete);
//            taskDao.deleteById(idOfTaskToDelete);
//            return new ModelAndView(model, "success.hbs");
//        }, new HandlebarsTemplateEngine());
//    }
}