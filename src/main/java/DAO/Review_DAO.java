package DAO;


import models.*;

import java.util.List;

public interface Review_DAO {

    void add (Review review);

    List<Review> getAll();

    Review findById(int id);

    void update(int id, String review);


    void deleteById(int id);
    void clearAllreviews();
}
