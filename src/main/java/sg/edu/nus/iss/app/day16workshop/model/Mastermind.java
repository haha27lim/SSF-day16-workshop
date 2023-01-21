package sg.edu.nus.iss.app.day16workshop.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Random;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;


public class Mastermind implements Serializable {
    private String name;
    private Pieces pieces;
    private String id;
    private int insertCount;
    private int updateCount;
    private boolean isUpSert;

    // generate random id
    public Mastermind() {
        this.id = generateId(8);
    }

    // method to generate random id
    public synchronized String generateId(int numChars) {
        Random r = new Random();
        StringBuilder strBuilder = new StringBuilder();
        // Continuously generate random hexadecimal numbers and append them to the string builder
        // until the desired number of characters is reached
        while (strBuilder.length() < numChars) {
            strBuilder.append(Integer.toHexString(r.nextInt()));
        }
        // Return the first numChars characters of the generated id
        return strBuilder.toString().substring(0, numChars);
    }

    // getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getInsertCount() {
        return insertCount;
    }

    public void setInsertCount(int insertCount) {
        this.insertCount = insertCount;
    }

    public int getUpdateCount() {
        return updateCount;
    }

    public void setUpdateCount(int updateCount) {
        this.updateCount = updateCount;
    }

    public boolean isUpSert() {
        return isUpSert;
    }

    public void setUpSert(boolean isUpSert) {
        this.isUpSert = isUpSert;
    }

    public Pieces getPieces() {
        return pieces;
    }

    public void setPieces(Pieces pieces) {
        this.pieces = pieces;
    }

    // method to convert object to JSON
    public JsonObject toJSON() {
        return Json.createObjectBuilder()
                .add("name", this.getName())
                .add("pieces", this.getPieces().toJSON())
                .add("id", this.getId())
                .build();
    }

    // method to convert object to JSON for insert
    public JsonObject toJSONInsert() {
        return Json.createObjectBuilder()
                .add("id", this.getId())
                .add("insert_count", this.getInsertCount())
                .build();
    }

    // method to convert object to JSON for update
    public JsonObject toJSONUpdate() {
        return Json.createObjectBuilder()
                .add("id", this.getId())
                .add("update_count", this.getUpdateCount())
                .build();
    }

    // static method to create an object from json
    public static Mastermind create(String json) throws IOException {
        Mastermind m = new Mastermind();
        if(json != null)
            try (InputStream is = new ByteArrayInputStream(json.getBytes())) {
                JsonReader r = Json.createReader(is);
                JsonObject o = r.readObject();
                m.setName(o.getString("name"));
                JsonObject pieces = o.getJsonObject("pieces");
                m.setPieces(Pieces.createJson(pieces));
            }
        return m;
    }

    // overridden toString method to return the id and name of the object
    @Override
    public String toString() {
        return this.getId() + " " + this.getName();
    }
}
