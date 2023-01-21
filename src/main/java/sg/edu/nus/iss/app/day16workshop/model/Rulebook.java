package sg.edu.nus.iss.app.day16workshop.model;

import java.io.Serializable;

import jakarta.json.Json;
import jakarta.json.JsonNumber;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

public class Rulebook implements Serializable {
    private int total_count;
    private String file;

    // getters and setters
    public String getFile() {
        return file;
    }

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public JsonObjectBuilder toJSON() {
        // create a JsonObjectBuilder
        return Json.createObjectBuilder()
                .add("total_count", this.getTotal_count())
                .add("file", this.getFile());
    }

    public static Rulebook createJson(JsonObject o) {
        Rulebook rb = new Rulebook();
        JsonNumber totalCnt = o.getJsonNumber("total_count");
        String file = o.getString("file");
        // set the total_count of the Rulebook object using the intValue method
        rb.total_count = totalCnt.intValue();
        // set the file of the Rulebook object
        rb.file = file;
        // return the Rulebook object
        return rb;
    }

}
