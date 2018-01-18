package models;

public class Hike {
    private int idHike;
    private String hike;
    private String description;
    private int averageRating;
    private int numberOfReviews;
    private int idLocation;
    private String dateCreated;


    public Hike(String hike, String description) {
        this.hike = hike;
        this.description = description;
    }

    public int getIdHike() {
        return idHike;
    }

    public void setIdHike(int idHike) {
        this.idHike = idHike;
    }

    public String getName() {
        return hike;
    }

    public void setName(String name) {
        this.hike = hike;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(int averageRating) {
        this.averageRating = averageRating;
    }

    public int getNumberOfReviews() {
        return numberOfReviews;
    }

    public void setNumberOfReviews(int numberOfReviews) {
        this.numberOfReviews = numberOfReviews;
    }

    public int getIdLocation() {
        return idLocation;
    }

    public void setIdLocation(int idLocation) {
        this.idLocation = idLocation;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Hike hike = (Hike) o;

        if (idHike != hike.idHike) return false;
        if (averageRating != hike.averageRating) return false;
        if (numberOfReviews != hike.numberOfReviews) return false;
        if (idLocation != hike.idLocation) return false;
        if (!hike.equals(hike.hike)) return false;
        if (!description.equals(hike.description)) return false;
        return dateCreated != null ? dateCreated.equals(hike.dateCreated) : hike.dateCreated == null;
    }

    @Override
    public int hashCode() {
        int result = idHike;
        result = 31 * result + hike.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + averageRating;
        result = 31 * result + numberOfReviews;
        result = 31 * result + idLocation;
        result = 31 * result + (dateCreated != null ? dateCreated.hashCode() : 0);
        return result;
    }
}
