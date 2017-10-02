package pe.edu.upc.homeassistant.model;

import android.graphics.Bitmap;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class Client {

    private String name;
    private String address;
    private String mail;
    private String phone;
    private double latitude;
    private double longitude;
    private String password;
    private String urlPhoto;
    private Bitmap photo;

    public String getName() {
        return name;
    }

    public Client setName(String name) {
        this.name = name;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Client setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getMail() {
        return mail;
    }

    public Client setMail(String mail) {
        this.mail = mail;
        return this;
    }

    public double getLatitude() {
        return latitude;
    }

    public Client setLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    public double getLongitude() {
        return longitude;
    }

    public Client setLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Client setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public Client setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public Client setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public Client setPhoto(Bitmap photo) {
        this.photo = photo;
        return this;
    }

    public Client(String name, String address, String mail, String phone, double latitude, double longitude, String password, String urlPhoto) {
        this.name = name;
        this.address = address;
        this.mail = mail;
        this.phone = phone;
        this.latitude = latitude;
        this.longitude = longitude;
        this.password = password;
        this.urlPhoto = urlPhoto;
    }

    public Client() {
    }

    public static Client from(JSONObject jsonObject){
        Client client = null;
        try {
            client = new Client();
            client.setName(jsonObject.getString("name"))
                    .setAddress(jsonObject.getString("address"))
                    .setMail(jsonObject.getString("address"))
                    .setPhone(jsonObject.getString("phone"))
                    .setLatitude(jsonObject.getLong("latitude"))
                    .setLongitude(jsonObject.getLong("longitude"))
                    .setUrlPhoto(jsonObject.getString("url_photo"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return client;
    }
}
