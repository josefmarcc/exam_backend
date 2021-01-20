package DTO;

/**
 *
 * @author Josef Marc Pedersen <cph-jp325@cphbusiness.dk>
 */
public class HotelDTO {

    private int id;
    private String address;
    private String phone;
    private String content;
    private String email;
    private String title;
    private String url;
    private String price;
    private String directions;
    private GeoDTO geo;

    public HotelDTO(int id, String address, String phone, String content, String email, String title, String url, String price, String directions, GeoDTO geo) {
        this.id = id;
        this.address = address;
        this.phone = phone;
        this.content = content;
        this.email = email;
        this.title = title;
        this.url = url;
        this.price = price;
        this.directions = directions;
        this.geo = geo;

    }

    public GeoDTO getGeo() {
        return geo;
    }

    public void setGeo(GeoDTO geo) {
        this.geo = geo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

}
