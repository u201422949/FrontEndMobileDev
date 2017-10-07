package pe.edu.upc.homeassistant.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Skill implements Serializable{

    private int code;
    private String name;

    public int getCode() {
        return code;
    }

    public Skill setCode(int code) {
        this.code = code;
        return this;
    }

    public String getName() {
        return name;
    }

    public Skill setName(String name) {
        this.name = name;
        return this;
    }

    public Skill(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public Skill() {
    }

    public String toString(){
        return name;
    }

    public static Skill from(JSONObject jsonSource){
        Skill article = new Skill();
        try {
            article.setCode(jsonSource.getInt("code"))
                    .setName(jsonSource.getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return article;
    }
}
