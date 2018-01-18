package DAO;

import models.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;


public class Review_DAO_SQL2O implements Review_DAO {

    private final Sql2o sql2o;

    public Review_DAO_SQL2O(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Review review) {
        String sql = "INSERT INTO reviews (review, title, rating) VALUES (:review, :title, :rating)";
        try (Connection con = sql2o.open()) {
            int idReview = (int) con.createQuery(sql)
                    .bind(review)
                    .executeUpdate()
                    .getKey();
            review.setIdReview(idReview);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<Review> getAll() {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM reviews")
                    .executeAndFetch(Review.class);
        }
        }

    @Override
    public Review findById(int id) {
        return null;
    }

    @Override
    public void update(int id, String review) {

    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public void clearAllreviews() {

    }

}
