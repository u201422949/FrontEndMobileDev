package pe.edu.upc.homeassistant.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Fernando on 07/10/2017.
 */

public class Budge implements Serializable {

    private Expert expert;
    private double price;

    public Expert getExpert() {
        return expert;
    }

    public void setExpert(Expert expert) {
        this.expert = expert;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Budge() {
    }

    public static Budge from(JSONObject jsonSource){
        Budge budge = new Budge();
        try {
            budge.setPrice(jsonSource.getInt("precio"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return budge;
    }
}
