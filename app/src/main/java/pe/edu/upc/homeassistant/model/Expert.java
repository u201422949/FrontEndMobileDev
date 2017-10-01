package pe.edu.upc.homeassistant.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Fernando on 23/09/2017.
 */

public class Expert implements Serializable{

    private String name;
    private String mail;
    private long date;
    private double gender;
    private String password;
    private double rate;
    private List<Skill> skills;
    private String photo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public double getGender() {
        return gender;
    }

    public void setGender(double gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public Expert(String name, String mail) {
        this.name = name;
        this.mail = mail;
    }
}
