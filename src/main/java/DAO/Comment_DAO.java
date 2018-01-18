package DAO;


import models.*;

import java.util.List;

public interface Comment_DAO {

    void add (Comment comment);

    List<Comment> getAll();

    Comment findById(int idComment);

    void update(String comment, int idComment);


    void deleteById(int idComment);

    void clearAllComments();
}
