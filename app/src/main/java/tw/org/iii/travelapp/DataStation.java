package tw.org.iii.travelapp;

/**
 * Created by MacGyver on 2018/4/11.
 */

public class DataStation {
    String description;
    String img_url;
    String cat2;
    String xbody;
    String address;
    String stitle;
    double lng, lat;
    String memo_time;

    public DataStation(){

    }

    public DataStation(String description, String img_url) {
        this.description = description;
        this.img_url = img_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getCat2() {
        return cat2;
    }

    public void setCat2(String cat2) {
        this.cat2 = cat2;
    }

    public String getXbody() {
        return xbody;
    }

    public void setXbody(String xbody) {
        this.xbody = xbody;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStitle() {
        return stitle;
    }

    public void setStitle(String stitle) {
        this.stitle = stitle;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getMemo_time() {
        return memo_time;
    }

    public void setMemo_time(String memo_time) {
        this.memo_time = memo_time;
    }
}
