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
        //  /locations/:idLocation
        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();

            List<Location> allLocations = locationDao.getAll();
            model.put("locations", allLocations);

            List<Hike> hikes = hikeDao.getAll();
            model.put("hikes", hikes);
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        //get: show a form to create a new category
        //  /locations/new
        get("/locations/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Location> locations = locationDao.getAll(); //refresh list of links for navbar.
            model.put("locations", locations);
            return new ModelAndView(model, "location-form.hbs"); //new
        }, new HandlebarsTemplateEngine());

        //post: process a form to create a new category
        //  /locations
        post("/locations", (request, response) -> { //new
            Map<String, Object> model = new HashMap<>();
            String hike = request.queryParams("hike");
            Location newLocation = new Location(hike);
            locationDao.add(newLocation);
            List<Location> locations = locationDao.getAll(); //refresh list of links for navbar.
            model.put("locations", locations);
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());

        //get: delete all locations and all tasks
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
        //  /locations/update
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
        //  /locations/:idLocation/hikes/:idHike
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

       //get: show all tasks in all locations and show all locations
       get("/", (req, res) -> {
           Map<String, Object> model = new HashMap<>();
           List<Hike> hikes = hikeDao.getAll();
           model.put("hikes", hikes);
           return new ModelAndView(model, "index.hbs");
       }, new HandlebarsTemplateEngine());

       //get: delete all tasks
       get("/hikes/delete", (req, res) -> {
           Map<String, Object> model = new HashMap<>();
           List<Location> allLocations = locationDao.getAll();
           model.put("locations", allLocations);
           hikeDao.clearAllHikes();
           return new ModelAndView(model, "success.hbs");
       }, new HandlebarsTemplateEngine());

       //get: show new task form
       get("/hikes/new", (req, res) -> {
           Map<String, Object> model = new HashMap<>();
           List<Location> allLocations = locationDao.getAll();
           model.put("locations", allLocations);
           return new ModelAndView(model, "hike-form.hbs");
       }, new HandlebarsTemplateEngine());

       //post: process new task form
       post("/hikes/new", (request, response) -> {
           Map<String, Object> model = new HashMap<>();
           List<Location> allLocations = locationDao.getAll();
           model.put("locations", allLocations);
           String hike = request.queryParams("hike");
           String description = request.queryParams("description");
           int idLocation = Integer.parseInt(request.queryParams("idLocation"));
           Hike newHike = new Hike(hike, description, idLocation ); //ignore the hardcoded idLocation
           hikeDao.add(newHike);
           model.put("hike", newHike);
           return new ModelAndView(model, "success.hbs");
       }, new HandlebarsTemplateEngine());

       //get: show an individual task that is nested in a category
       get("/locations/:idLocation/hikes/:idHike", (req, res) -> {
           Map<String, Object> model = new HashMap<>();
           int idOfHikeToFind = Integer.parseInt(req.params("idHike"));
           Hike foundHike = hikeDao.findById(idOfHikeToFind);
           model.put("task", foundHike);
           return new ModelAndView(model, "task-detail.hbs");
       }, new HandlebarsTemplateEngine());

       //get: show a form to update a task
       get("/hikes/update", (req, res) -> {
           Map<String, Object> model = new HashMap<>();
           List<Location> allLocations = locationDao.getAll();
           model.put("locations", allLocations);
           List<Hike> allHikes = hikeDao.getAll();
           model.put("hikes", allHikes);
           model.put("editHike", true);
           return new ModelAndView(model, "hike-form.hbs");
       }, new HandlebarsTemplateEngine());

       //post: process a form to update a task
       post("/hikes/update", (req, res) -> {
           Map<String, Object> model = new HashMap<>();
           List<Location> allLocations = locationDao.getAll();
           model.put("locations", allLocations);
           String newHike = req.queryParams("hike");
           String newDescription = req.queryParams("description");
           int newIdLocation = Integer.parseInt(req.queryParams("idLocation"));
           int idHikeToEdit = Integer.parseInt(req.queryParams("idHikeToEdit"));
           Hike editHike = hikeDao.findById(idHikeToEdit);
           hikeDao.update(idHikeToEdit, newHike, newDescription, newIdLocation);
           return new ModelAndView(model, "success.hbs");
       }, new HandlebarsTemplateEngine());

       //get: delete an individual task
       get("locations/:idLocation/hikes/:idHike/delete", (req, res) -> {
           Map<String, Object> model = new HashMap<>();
           int idOfHikeToDelete = Integer.parseInt(req.params("idHike"));
           Hike deleteHike = hikeDao.findById(idOfHikeToDelete);
           hikeDao.deleteById(idOfHikeToDelete);
           return new ModelAndView(model, "success.hbs");
       }, new HandlebarsTemplateEngine());
   }
}
