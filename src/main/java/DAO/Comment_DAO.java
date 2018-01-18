package DAO;


import models.*;

import java.util.List;

public interface Comment_DAO {

    void add (Comment comment);

    List<Comment> getAll();

    Comment findById(int id);

    void update(int id, String comment);


    void deleteById(int id);
    void clearAllCategories();
}
