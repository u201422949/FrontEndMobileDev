package pe.edu.upc.homeassistant.model;

import java.io.Serializable;

/**
 * Created by Fernando on 23/09/2017.
 */

public class RequestType implements Serializable{

    private int code;
    private String name;

    public int getCode() {
        return code;
    }

    public RequestType setCode(int code) {
        this.code = code;
        return this;
    }

    public String getName() {
        return name;
    }

    public RequestType setName(String name) {
        this.name = name;
        return this;
    }

    public RequestType(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public RequestType() {
    }

}
