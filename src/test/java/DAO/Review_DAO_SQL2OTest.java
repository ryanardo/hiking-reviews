package DAO;

import models.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;


import static org.junit.Assert.*;
import static junit.framework.TestCase.assertEquals;

public class Review_DAO_SQL2OTest {

    private Review_DAO_SQL2O review_DAO;
    private Connection conn;

    public Review setupNewReview() {
        return new Review("TERRIBLE", "Kiss my GRITS!", 10);
    }

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        review_DAO = new Review_DAO_SQL2O(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void addReviewSetsId() throws Exception {
        Review testReview = setupNewReview();
        int originalReviewId = testReview.getIdReview();
        review_DAO.add(testReview);
        assertNotEquals(originalReviewId, testReview.getIdReview());
    }

    @Test
    public void getAll() throws Exception {
        Review testReview = setupNewReview();
        Review testReview2 = setupNewReview();
        review_DAO.add(testReview);
        review_DAO.add(testReview2);
        assertEquals(2, review_DAO.getAll().size());
    }

    @Test
    public void findById() throws Exception {
        Review testReview = setupNewReview();
        Review testReview2 = new Review("Review-2", "Title-2", 8);
        review_DAO.add(testReview);
        review_DAO.add(testReview2);
        assertEquals(8, review_DAO.findById(2).getRating());
    }

    @Test
    public void update() throws Exception {
        Review testReview = setupNewReview();
        review_DAO.add(testReview);
        review_DAO.update(1, "Review-2.1");
        assertEquals("Review-2.1", review_DAO.findById(1).getReview());
    }

    @Test
    public void deleteById() throws Exception {
        Review testReview = setupNewReview();
        review_DAO.add(testReview);
        review_DAO.deleteById(testReview.getIdReview());
        assertEquals(0, review_DAO.getAll().size());
    }

    @Test
    public void clearAllReviews() throws Exception {
        Review testReview = setupNewReview();
        Review testReview2 = new Review("Review-2", "Title-2", 8);
        review_DAO.add(testReview);
        review_DAO.add(testReview2);
        review_DAO.clearAllReviews();
        assertEquals(0, review_DAO.getAll().size());
    }

}