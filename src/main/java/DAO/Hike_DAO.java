package DAO;


import models.*;

import java.util.List;

public interface Hike_DAO {
    void add (Hike hike);

    List<Hike> getAll();

    Hike findById(int id);

    void update(int id, String comment);


    void deleteById(int id);
    void clearAllHikes();
}
