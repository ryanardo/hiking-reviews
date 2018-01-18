package models;


public class Location {
    private int idLocation;
    private String location;
    private String dateCreated;


    public Location(String location) {
        this.location = location;
    }

    public int getIdLocation() {
        return idLocation;
    }

    public void setIdLocation(int idLocation) {
        this.idLocation = idLocation;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

        Location location1 = (Location) o;

        if (idLocation != location1.idLocation) return false;
        if (!location.equals(location1.location)) return false;
        return dateCreated != null ? dateCreated.equals(location1.dateCreated) : location1.dateCreated == null;
    }

    @Override
    public int hashCode() {
        int result = idLocation;
        result = 31 * result + location.hashCode();
        result = 31 * result + (dateCreated != null ? dateCreated.hashCode() : 0);
        return result;
    }
}