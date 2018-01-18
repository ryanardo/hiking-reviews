package DAO;

import models.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Hike_DAO_SQL2O implements Hike_DAO {

    private final Sql2o sql2o;

    public Hike_DAO_SQL2O(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Hike hike) {
        String sql = "INSERT INTO hikes (hike, description) VALUES (:hike, :description)";
        try (Connection con = sql2o.open()) {
            int idHike = (int) con.createQuery(sql)
                    .bind(hike)
                    .executeUpdate()
                    .getKey();
            hike.setIdHike(idHike);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<Hike> getAll() {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM hikes")
                    .executeAndFetch(Hike.class);
        }
    }

    @Override
    public Hike findById(int idHike) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM hikes WHERE idHike = :idHike")
                    .addParameter("idHike", idHike)
                    .executeAndFetchFirst(Hike.class);
        }
    }

    @Override
    public void update(int idHike, String hike) {
        String sql = "UPDATE hikes SET hike  = :hike WHERE idHike = :idHike";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("hike", hike)
                    .addParameter("idHike", idHike)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteById(int idHike) {
        String sql = "DELETE from hikes WHERE idHike = :idHike";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("idHike", idHike)
                    .executeUpdate();
        }catch (Sql2oException ex){
            System.out.println(ex);
        }

    }

    @Override
    public void clearAllHikes() {
        String sql = "DELETE FROM hikes";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql).executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
}



