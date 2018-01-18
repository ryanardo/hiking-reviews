package DAO;

import models.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Location_DAO_SQL2O implements Location_DAO{

    private final Sql2o sql2o;

    public Location_DAO_SQL2O(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Location location) {
        String sql = "INSERT INTO locations (location) VALUES (:location)";
        try (Connection con = sql2o.open()) {
            int idLocation = (int) con.createQuery(sql)
                    .bind(location)
                    .executeUpdate()
                    .getKey();
            location.setIdLocation(idLocation);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<Location> getAll() {
            try (Connection con = sql2o.open()) {
                return con.createQuery("SELECT * FROM locations")
                        .executeAndFetch(Location.class);
            }
    }

    @Override
    public Location findById(int idLocation) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM locations WHERE idLocation = :idLocation")
                    .addParameter("idLocation", idLocation)
                    .executeAndFetchFirst(Location.class);
        }
    }

    @Override
    public void update(int idLocation, String location) {
        String sql = "UPDATE locations SET location  = :location WHERE idLocation = :idLocation";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("location", location)
                    .addParameter("idLocation", idLocation)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }

    }

    @Override
    public void deleteById(int idLocation) {
        String sql = "DELETE from locations WHERE idLocation = :idLocation";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("idLocation", idLocation)
                    .executeUpdate();
        }catch (Sql2oException ex){
            System.out.println(ex);
        }


    }

    @Override
    public void clearAllLocations() {
        String sql = "DELETE FROM locations";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql).executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }

    }
}
