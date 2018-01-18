package DAO;

import models.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;
import static junit.framework.TestCase.assertEquals;

public class Comment_DAO_SQL2OTest {

    private Comment_DAO_SQL2O comment_DAO;
    private Connection conn;

    public Comment setupNewComment() {
        return new Comment("this here");
    }

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        comment_DAO = new Comment_DAO_SQL2O(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void addCommentSetsId() throws Exception {
        Comment testComment = setupNewComment();
        int originalCommentId = testComment.getIdComment();
        comment_DAO.add(testComment);
        assertNotEquals(originalCommentId, testComment.getIdComment());
    }

    @Test
    public void getAll() throws Exception {
        Comment testComment = setupNewComment();
        Comment testComment2 = setupNewComment();
        comment_DAO.add(testComment);
        comment_DAO.add(testComment2);
        assertEquals(2, comment_DAO.getAll().size());
    }

    @Test
    public void findById() throws Exception {
        Comment testComment = setupNewComment();
        Comment testComment2 = new Comment("Review-2");
        comment_DAO.add(testComment);
        comment_DAO.add(testComment2);
        assertEquals(2, comment_DAO.findById(2).getIdComment());
    }

    @Test
    public void update() throws Exception {
        Comment testComment = setupNewComment();
        comment_DAO.add(testComment);
        comment_DAO.update("Comment-2.1", 1);
        assertEquals("Comment-2.1", comment_DAO.findById(1).getComment());
    }

    @Test
    public void deleteById() throws Exception {
        Comment testComment = setupNewComment();
        comment_DAO.add(testComment);
        comment_DAO.deleteById(testComment.getIdComment());
        assertEquals(0, comment_DAO.getAll().size());
    }

    @Test
    public void clearAllComments() throws Exception {
        Comment testComment = setupNewComment();
        Comment testComment2 = new Comment("Comment-2");
        comment_DAO.add(testComment);
        comment_DAO.add(testComment2);
        comment_DAO.clearAllComments();
        assertEquals(0, comment_DAO.getAll().size());
    }

}