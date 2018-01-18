package DAO;

import models.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;


import static org.junit.Assert.*;
import static junit.framework.TestCase.assertEquals;

public class Location_DAO_SQL2OTest {

    private Location_DAO_SQL2O location_DAO;
    private Review_DAO_SQL2O review_DAO;
    private Connection conn;

    public Location setupNewLocation() {
        return new Location("something");
    }

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        location_DAO = new Location_DAO_SQL2O(sql2o);
        review_DAO = new Review_DAO_SQL2O(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void addLocationSetsId() throws Exception {
        Location testLocation = setupNewLocation();
        int originalLocationId = testLocation.getIdLocation();
        location_DAO.add(testLocation);
        assertNotEquals(originalLocationId, testLocation.getIdLocation());
    }

    @Test
    public void getAll() throws Exception {
        Location testLocation = setupNewLocation();
        Location testLocation2 = setupNewLocation();
        location_DAO.add(testLocation);
        location_DAO.add(testLocation2);
        assertEquals(2, location_DAO.getAll().size());
    }

    @Test
    public void findById() throws Exception {
        Location testLocation = setupNewLocation();
        Location testLocation2 = new Location("Location-2");
        location_DAO.add(testLocation);
        location_DAO.add(testLocation2);
        assertEquals("Location-2", location_DAO.findById(2).getLocation());
    }

    @Test
    public void update() throws Exception {
        Location testLocation = setupNewLocation();
        location_DAO.add(testLocation);
        location_DAO.update(1, "Review-2.1");
        assertEquals("Review-2.1", location_DAO.findById(1).getLocation());
    }

    @Test
    public void deleteById() throws Exception {
        Location testLocation = setupNewLocation();
        location_DAO.add(testLocation);
        location_DAO.deleteById(testLocation.getIdLocation());
        assertEquals(0, location_DAO.getAll().size());
    }

    @Test
    public void clearAllLocation() throws Exception {
        Location testLocation = setupNewLocation();
        Location testLocation2 = new Location("Review-2");
        location_DAO.add(testLocation);
        location_DAO.add(testLocation2);
        location_DAO.clearAllLocations();
        assertEquals(0, location_DAO.getAll().size());
    }





}