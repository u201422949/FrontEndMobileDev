package pe.edu.upc.homeassistant.model;

import java.io.Serializable;

public class Request implements Serializable{

    private Client client;
    private Expert expert;
    private RequestType requestType;
    private String description;
    private String subject;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Expert getExpert() {
        return expert;
    }

    public void setExpert(Expert expert) {
        this.expert = expert;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
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

    public Request(RequestType requestType, String description, String subject) {
        this.requestType = requestType;
        this.description = description;
        this.subject = subject;
    }

    public Request(Client client, RequestType requestType, String description, String subject) {
        this.client = client;
        this.requestType = requestType;
        this.description = description;
        this.subject = subject;
    }

    public Request(Client client, Expert expert, RequestType requestType, String description, String subject) {
        this.client = client;
        this.expert = expert;
        this.requestType = requestType;
        this.description = description;
        this.subject = subject;
    }

    public Request() {
    }
}
