package sg.edu.nus.iss.app.day16workshop.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonNumber;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;


public class Pegs implements Serializable {
    private int total_count;
    private List<Type> types;

    // getters and setters
    public List<Type> getTypes() {
        return types;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    // method to convert the object to JSON
    public JsonObjectBuilder toJSON() {
        // Create a JsonArrayBuilder to hold the types
        JsonArrayBuilder arrbld = Json.createArrayBuilder();
        // Transform the list of types into a list of JsonObjectBuilders
        List<JsonObjectBuilder> listOfTypes = this.getTypes()
                .stream()
                .map(t -> t.toJSON())
                .toList();
        // Add each JsonObjectBuilder to the JsonArrayBuilder
        for (JsonObjectBuilder x : listOfTypes)
            arrbld.add(x);

        // Create and return a JsonObjectBuilder with the total count and types added
        return Json.createObjectBuilder()
                .add("total_count", this.getTotal_count())
                .add("types", arrbld);

    }

    public static Pegs createJson(JsonObject o) {
        Pegs pp = new Pegs();
        List<Type> result = new LinkedList<Type>();
        // Retrieve the total count of types from the json object
        JsonNumber totalCnt = o.getJsonNumber("total_count");
        // Retrieve the array of types from the json object
        JsonArray types = o.getJsonArray("types");
        // Set the total count on the Pegs instance
        pp.total_count = totalCnt.intValue();
        //Iterate through the array of types
        for (int i = 0; i < types.size(); i++) {
            // Retrieve the current type object from the array
            JsonObject x = types.getJsonObject(i);
            // Create a new Type instance from the json object
            Type t = Type.createJson(x);
            // Add the Type instance to the result list
            result.add(t);
        } //Set the types on the Pegs instance
        pp.types = result;
        //Return the Pegs instance
        return pp;
    }
}
