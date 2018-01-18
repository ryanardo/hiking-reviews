package models;

public class Review {
    private int idReview;
    private String title;
    private String review;
    private int rating;
    private int idHike;
    private String dateCreated;


    public Review(String review, String title, int rating) {
        this.review = review;
        this.title = title;
        this.rating = rating;
    }


    public int getIdReview() {
        return idReview;
    }

    public String getTitle() {
        return title;
    }

    public String getReview() {
        return review;
    }

    public int getRating() {
        return rating;
    }

    public int getIdHike() {
        return idHike;
    }

    public String getDateCreated() {
        return dateCreated;
    }


    public void setIdReview(int idReview) {
        this.idReview = idReview;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setIdHike(int idHike) {
        this.idHike = idHike;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Review review1 = (Review) o;

        if (idReview != review1.idReview) return false;
        if (rating != review1.rating) return false;
        if (idHike != review1.idHike) return false;
        if (!title.equals(review1.title)) return false;
        if (!review.equals(review1.review)) return false;
        return dateCreated != null ? dateCreated.equals(review1.dateCreated) : review1.dateCreated == null;
    }

    @Override
    public int hashCode() {
        int result = idReview;
        result = 31 * result + title.hashCode();
        result = 31 * result + review.hashCode();
        result = 31 * result + rating;
        result = 31 * result + idHike;
        result = 31 * result + (dateCreated != null ? dateCreated.hashCode() : 0);
        return result;
    }
}
