package pe.edu.upc.homeassistant.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import pe.edu.upc.homeassistant.Constants;
import pe.edu.upc.homeassistant.R;

import static android.content.Context.MODE_PRIVATE;

public class Client {

    private String id;
    private String name;
    private String address;
    private int documentNumber;
    private String mail;
    private String phone;
    private double latitude;
    private double longitude;
    private String password;
    private String urlPhoto;
    private byte[] photo;

    public int getDocumentNumber() {
        return documentNumber;
    }

    public Client setDocumentNumber(int documentNumber) {
        this.documentNumber = documentNumber;
        return this;
    }

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

    public byte[] getPhoto() {
        return photo;
    }

    public Client setPhoto(byte[] photo) {
        this.photo = photo;
        return this;
    }

    public Client(String id, String name, String address, String mail, String phone, double latitude, double longitude, String password, String urlPhoto) {
        this.id = id;
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

    public static Client from(JSONObject jsonObject) throws JSONException {
        Client client = null;
        try {
            client = new Client();
            client.setId(jsonObject.getString("clientId"))
                    .setName(jsonObject.getString("name"))
                    .setAddress(jsonObject.getString("address"))
                    .setDocumentNumber(jsonObject.getInt("documentNumber"))
                    .setMail(jsonObject.getString("email"))
                    .setPhone(jsonObject.getString("phone"))
                    .setLatitude(jsonObject.getLong("latitude"))
                    .setLongitude(jsonObject.getLong("longitude"))
                    .setUrlPhoto(jsonObject.getString("photo"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return client;
    }

<<<<<<< HEAD
    public String getId() {
        return id;
    }

    public Client setId(String id) {
        this.id = id;
        return this;
=======
    public static Client from(Context context){
        SharedPreferences preferences = context.getSharedPreferences
                (context.getString(R.string.app_name), MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString(Constants.SP_DATA_CLIENT, "");
        return gson.fromJson(json, Client.class);
>>>>>>> a115a5d8ea5452d1d2e874edeac7764f87f3f577
    }
}
