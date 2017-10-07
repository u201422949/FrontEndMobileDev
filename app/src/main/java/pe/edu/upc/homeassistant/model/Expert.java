package pe.edu.upc.homeassistant.model;

import android.graphics.Bitmap;
import android.os.Bundle;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Fernando on 23/09/2017.
 */

public class Expert implements Serializable{

    private String name;
    private String mail;
    private String description;
    private String phone;
    private String password;
    private float rate;
    private List<Skill> skills;
    private String urlPhoto;

    public String getName() {
        return name;
    }

    public Expert setName(String name) {
        this.name = name;
        return this;
    }

    public String getMail() {
        return mail;
    }

    public Expert setMail(String mail) {
        this.mail = mail;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Expert setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Expert setPassword(String password) {
        this.password = password;
        return this;
    }

    public float getRate() {
        return rate;
    }

    public Expert setRate(float rate) {
        this.rate = rate;
        return this;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public Expert setSkills(List<Skill> skills) {
        this.skills = skills;
        return this;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public Expert setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public Expert setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public Expert() {
    }

    public Expert(String name, String mail, String description, String phone, String password, float rate, List<Skill> skills, String urlPhoto) {
        this.name = name;
        this.mail = mail;
        this.description = description;
        this.phone = phone;
        this.password = password;
        this.rate = rate;
        this.skills = skills;
        this.urlPhoto = urlPhoto;
    }

    public Expert(String name, String mail, String description, String phone, float rate, List<Skill> skills) {
        this.name = name;
        this.mail = mail;
        this.description = description;
        this.phone = phone;
        this.rate = rate;
        this.skills = skills;
    }

    public Bundle toBundle(){
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        bundle.putString("mail", mail);
        bundle.putString("description", description);
        bundle.putString("phone", phone);
        bundle.putFloat("rate", rate);
        bundle.putSerializable("skills", (Serializable) skills);
        bundle.putString("urlPhoto", urlPhoto);
        return bundle;
    }

    public static Expert from(Bundle bundle){
        Expert expert = new Expert();
        expert.setName(bundle.getString("name"))
                .setMail(bundle.getString("mail"))
                .setDescription(bundle.getString("description"))
                .setRate(bundle.getFloat("rate"))
                .setPhone(bundle.getString("phone"))
                .setSkills((List<Skill>) bundle.getSerializable("skills"))
                .setUrlPhoto(bundle.getString("urlPhoto"));

        return expert;
    }

}
