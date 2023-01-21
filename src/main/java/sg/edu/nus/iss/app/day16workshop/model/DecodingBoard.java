package sg.edu.nus.iss.app.day16workshop.model;

import java.io.Serializable;

import jakarta.json.Json;
import jakarta.json.JsonNumber;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

public class DecodingBoard implements Serializable {
    private int total_count;

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    // method to convert the object to JSON
    public JsonObjectBuilder toJSON() {
        return Json.createObjectBuilder()
                .add("total_count", this.getTotal_count());
    }

    // method to create an object of type DecodingBoard and set its properties from the passed in JsonObject
    public static DecodingBoard createJson(JsonObject o) {
        DecodingBoard d = new DecodingBoard();
        // extract a JsonNumber object from the passed in JsonObject by looking for a property named "total_count"
        JsonNumber totalCnt = o.getJsonNumber("total_count");
        System.out.println(totalCnt);
        // set "total_count" property of DecodingBoard object to integer value of the extracted JsonNumber object
        d.total_count = totalCnt.intValue();
        return d;
    }
}
