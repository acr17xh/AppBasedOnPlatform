package sheffield.mbg.pojo;

public class Restaurant {
    private String name;
    private String vicinity;
    private String icon;
    private String place_id;
    private Double rating;

    private Double lat;
    private Double lng;

    private Restaurant_photo restaurant_photo;

    public Restaurant() {

    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Restaurant_photo getRestaurant_photo() {
        return restaurant_photo;
    }

    public void setRestaurant_photo(Restaurant_photo restaurant_photo) {
        this.restaurant_photo = restaurant_photo;
    }
}
