package DAO;


import models.*;

import java.util.List;

public interface Review_DAO {

    void add (Review review);

    List<Review> getAll();

    Review findById(int idReview);

    void update(int idReview, String review);


    void deleteById(int idReview);

    void clearAllReviews();
}
