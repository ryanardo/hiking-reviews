package DAO;

import models.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;


import static org.junit.Assert.*;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotSame;

public class Hike_DAO_SQL2OTest {

    private Hike_DAO_SQL2O hike_DAO;
    private Connection conn;

    public Hike setupNewHike() {
        return new Hike("Dog Mountain", "Hike just outside of Portland, Oregon.");
    }

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        hike_DAO = new Hike_DAO_SQL2O(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void addHikeSetsId() throws Exception {
        Hike testHike = setupNewHike();
        int originalHikeId = testHike.getIdHike();
        hike_DAO.add(testHike);
        assertNotEquals(originalHikeId, testHike.getIdHike());
    }

    @Test
    public void getAll() throws Exception {
        Hike testHike = setupNewHike();
        Hike testHike2 = setupNewHike();
        hike_DAO.add(testHike);
        hike_DAO.add(testHike2);
        assertEquals(2, hike_DAO.getAll().size());
    }

    @Test
    public void findById() throws Exception {
        Hike testHike = setupNewHike();
        Hike testHike2 = new Hike("Hike-2", "Description-2");
        hike_DAO.add(testHike);
        hike_DAO.add(testHike2);
        assertEquals("Description-2", hike_DAO.findById(2).getDescription());
    }

    @Test
    public void update() throws Exception {
        Hike testHike = setupNewHike();
        hike_DAO.add(testHike);
        hike_DAO.update(1, "Hike-2.1");
        assertEquals("Hike-2.1", hike_DAO.findById(1).getName());
    }

    @Test
    public void deleteById() throws Exception {
        Hike testHike = setupNewHike();
        hike_DAO.add(testHike);
        hike_DAO.deleteById(testHike.getIdHike());
        assertEquals(0, hike_DAO.getAll().size());
    }

    @Test
    public void clearAllHikes() throws Exception {
        Hike testHike = setupNewHike();
        Hike testHike2 = new Hike("Hike-2", "Description-2");
        hike_DAO.add(testHike);
        hike_DAO.add(testHike2);
        hike_DAO.clearAllHikes();
        assertEquals(0, hike_DAO.getAll().size());
    }

}