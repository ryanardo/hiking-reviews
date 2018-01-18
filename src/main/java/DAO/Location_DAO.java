package DAO;


import models.*;

import java.util.List;

public interface Location_DAO {
    void add (Location location);

    List<Location> getAll();

    Location findById(int id);

    void update(int id, String location);


    void deleteById(int id);
    void clearAllLocations();
}
