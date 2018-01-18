package models;


public class Comment {

    private String comment;
    private String dateCreated;
    private int idComment;
    private int idReview;

    public Comment(String comment) {
        this.comment = comment;
    }

    //GETTER
    public String getComment() {
        return comment;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public int getIdComment() {
        return idComment;
    }

    public int getIdReview() {
        return idReview;
    }
    //SETTER
    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setIdComment(int idComment) {
        this.idComment = idComment;
    }

    public void setIdReview(int idReview) {
        this.idReview = idReview;
    }

    //EQUALS HASH CODE
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comment comment1 = (Comment) o;

        if (idComment != comment1.idComment) return false;
        if (idReview != comment1.idReview) return false;
        if (!comment.equals(comment1.comment)) return false;
        return dateCreated != null ? dateCreated.equals(comment1.dateCreated) : comment1.dateCreated == null;
    }

    @Override
    public int hashCode() {
        int result = comment.hashCode();
        result = 31 * result + (dateCreated != null ? dateCreated.hashCode() : 0);
        result = 31 * result + idComment;
        result = 31 * result + idReview;
        return result;
    }
}
