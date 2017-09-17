package pe.edu.upc.homeassistant.models;

public class Request {

    private User user;
    private String description;
    private String title;

    public User getUser() {
        return user;
    }

    public Request setUser(User user) {
        this.user = user;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Request setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Request setTitle(String title) {
        this.title = title;
        return this;
    }

    public Request(User user, String description, String title) {
        this.user = user;
        this.description = description;
        this.title = title;
    }

    public Request() {
    }
}
