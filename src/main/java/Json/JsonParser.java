package Json;

import java.io.FileReader;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;

public class JsonParser {
 
    private static JsonParser instance;
    private JsonArray jsonEnrollmentOptions;
    private ArrayList<String> enrollmentOptions;

    private JsonParser() {
        loadJson();
        enrollmentOptions = toArray(jsonEnrollmentOptions);
    }

    public static synchronized JsonParser getInstance() {
        if (instance == null) {
            instance = new JsonParser();
        }
        return instance;
    }

    private void loadJson() {
        String filePath = "src/main/java/Json/configuration.json";
        try (JsonReader reader = Json.createReader(new FileReader(filePath))) {
            JsonObject jsonParser = reader.readObject();
            jsonEnrollmentOptions = jsonParser.getJsonArray("enrollment");
        } catch (Exception exception) {}
    }

    private ArrayList<String> toArray (JsonArray jsonArray) {
        if (jsonArray == null){
            return null;
        }
        int beginning = 1;
        ArrayList<String> array = new ArrayList<>();
        for (JsonValue element : jsonArray){
            String data = element.toString();
            array.add(data.substring(beginning, data.length()-1));
        }
        return array;
    }

    public ArrayList<String> getEnrollmentOptions() {
        if (this.enrollmentOptions == null){
            return new ArrayList<>();
        }
        return this.enrollmentOptions;
    }
}