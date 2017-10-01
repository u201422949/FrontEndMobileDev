package pe.edu.upc.homeassistant.model;

import android.os.Bundle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Request implements Serializable{

    private Client client;
    private List<Expert> experts;
    private Skill skill;
    private String description;
    private String subject;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
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


}
