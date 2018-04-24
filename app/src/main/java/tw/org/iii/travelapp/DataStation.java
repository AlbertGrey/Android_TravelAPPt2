package tw.org.iii.travelapp;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by MacGyver on 2018/4/11.
 */

public class DataStation extends HashSet<String>{
    String total_id, stitle, name, type, CAT2, img_url,
            MEMO_TIME, address, xbody;
    HashMap photo;
    double lat, lng;
    ArrayList<String> photo_url;

    public DataStation(){

    }

    public DataStation(String total_id, String stitle, String img_url, String xbody,
                       double lat, double lng, String address, String MEMO_TIME) {
        this.total_id = total_id;
        this.stitle = stitle;
        this.img_url = img_url;
        this.address = address;
        this.xbody = xbody;
        this.lat = lat;
        this.lng = lng;
        this.MEMO_TIME = MEMO_TIME;
    }

    public DataStation(String total_id, String stitle, ArrayList<String> photo_url, String xbody,
                         double lat, double lng, String address, String MEMO_TIME) {
        this.total_id = total_id;
        this.stitle = stitle;
        this.photo_url = photo_url;
        this.address = address;
        this.xbody = xbody;
        this.lat = lat;
        this.lng = lng;
        this.MEMO_TIME = MEMO_TIME;
    }

    public DataStation(String total_id, String name, String type, String CAT2,
                       String MEMO_TIME, String address, String xbody,
                       double lat, double lng, ArrayList<String> photo_url) {
        this.total_id = total_id;
        this.name = name;
        this.type = type;
        this.CAT2 = CAT2;
        this.MEMO_TIME = MEMO_TIME;
        this.address = address;
        this.xbody = xbody;
        this.lat = lat;
        this.lng = lng;
        this.photo = photo;
        this.photo_url = photo_url;
    }

    public String getTotal_id() {
        return total_id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getCAT2() {
        return CAT2;
    }

    public String getMEMO_TIME() {
        return MEMO_TIME;
    }

    public String getAddress() {
        return address;
    }

    public String getXbody() {
        return xbody;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public HashMap getPhoto() {
        return photo;
    }

    public String getStitle() {
        return stitle;
    }

    public String getImg_url() {
        return img_url;
    }

    public ArrayList<String> getPhoto_url() {
        return photo_url;
    }
}
