package pe.edu.upc.homeassistant.model;

import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Request implements Serializable{

    private int id;
    private Client client;
    private List<Expert> experts;
    private List<Budge> budges;
    private Skill skill;
    private String description;
    private String subject;

    public int getId() {
        return id;
    }

    public Request setId(int id) {
        this.id = id;
        return this;
    }

    public List<Expert> getExperts() {
        return experts;
    }

    public Request setExperts(List<Expert> experts) {
        this.experts = experts;
        return this;
    }

    public Client getClient() {
        return client;
    }

    public Request setClient(Client client) {
        this.client = client;
        return this;
    }

    public Skill getSkill() {
        return skill;
    }

    public Request setSkill(Skill skill) {
        this.skill = skill;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Request setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public Request setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public List<Budge> getBudges() {
        return budges;
    }

    public Request setBudges(List<Budge> budges) {
        this.budges = budges;
        return this;
    }

    public Request(Skill skill, String description, String subject) {
        this.skill = skill;
        this.description = description;
        this.subject = subject;
    }

    public Request(Client client, Skill skill, String description, String subject) {
        this.client = client;
        this.skill = skill;
        this.description = description;
        this.subject = subject;
    }

    public Request() {
    }

    public static Request from(JSONObject jsonSource){
        Request request = new Request();
        List<Budge> budges = new ArrayList<>();

        try {
            JSONArray jsonArray = jsonSource.getJSONArray("cotizaciones");

            for (int i = 0; i < jsonArray.length(); i++)
                budges.add(Budge.from(jsonArray.getJSONObject(i)));

            request.setId(jsonSource.getInt("idsolicitud"))
                    .setDescription(jsonSource.getString("descripcion"))
                    .setSubject(jsonSource.getString("asunto"))
                    .setBudges(budges);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return request;
    }
}
