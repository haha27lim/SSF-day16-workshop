package sg.edu.nus.iss.app.day16workshop.model;

import java.io.Serializable;

import jakarta.json.Json;
import jakarta.json.JsonNumber;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

public class Type implements Serializable {
    private String type;
    private int count;

    // getters and setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public JsonObjectBuilder toJSON() {
        // create a JsonObjectBuilder
        return Json.createObjectBuilder()
                .add("type", this.getType())
                .add("count", this.getCount());

    }

    public static Type createJson(JsonObject o) {
        Type t = new Type();
        JsonNumber count = o.getJsonNumber("count");
        String type = o.getString("type");
        // set the count of the Type object using the intValue method
        t.count = count.intValue();
        // set the type of the Type object
        t.type = type;
        // return the Type object
        return t;
    }

}