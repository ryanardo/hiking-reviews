package DAO;

import org.sql2o.Sql2o;

/**
 * Created by Guest on 1/18/18.
 */
public class Location_DAO_SQL2O {

    private final Sql2o sql2o;

    public Location_DAO_SQL2O(Sql2o sql2o) {
        this.sql2o = sql2o;
    }
}
