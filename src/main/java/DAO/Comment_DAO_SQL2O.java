package DAO;

import models.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;


public class Comment_DAO_SQL2O implements Comment_DAO {

    private final Sql2o sql2o;

    public Comment_DAO_SQL2O(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Comment comment) {
        String sql = "INSERT INTO comments (comment, idReview) VALUES (:comment, :idReview)";
        try (Connection con = sql2o.open()) {
            int idComment = (int) con.createQuery(sql)
                    .bind(comment)
                    .executeUpdate()
                    .getKey();
            comment.setIdComment(idComment);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<Comment> getAll() {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM comments")
                    .executeAndFetch(Comment.class);
        }
    }

    @Override
    public Comment findById(int idComment) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM comments WHERE idComment = :idComment")
                    .addParameter("idComment", idComment)
                    .executeAndFetchFirst(Comment.class);
        }
    }

    @Override
    public void update(String comment, int idComment) {
        String sql = "UPDATE comments SET comment  = :comment WHERE idComment = :idComment";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("comment", comment)
                    .addParameter("idComment", idComment)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }


    @Override
    public void deleteById(int idComment) {
        String sql = "DELETE from comments WHERE idComment = :idComment";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("idComment", idComment)
                    .executeUpdate();
        }catch (Sql2oException ex){
            System.out.println(ex);
        }

    }

    @Override
    public void clearAllComments() {
        String sql = "DELETE FROM comments";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql).executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

}
