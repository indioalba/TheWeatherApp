package mtt.theweatherapp.model;

/**
 * Created by manuel on 20/3/16.
 */
public class City {

    String name;
    Double lat;
    Double longi;
    boolean isSelected;

    public City(String name, double lat, double longi, boolean isSelected) {
        this.name = name;
        this.lat = lat;
        this.longi = longi;
        this.isSelected = isSelected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLongi() {
        return longi;
    }

    public void setLongi(Double longi) {
        this.longi = longi;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
}
