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
        return new Location("vfd");
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


}