import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class movieDatabase {
    private final String databaseFile = "src/Movie.json";

    JSONObject database;

    public movieDatabase() {
        JSONParser parser = new JSONParser();

        try (FileReader jsonReader = new FileReader(databaseFile)) {
            Object obj = parser.parse(jsonReader);
            database = (JSONObject) obj;
        }

        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        catch (IOException e) {
            e.printStackTrace();

        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }

    }

    public void addMovie(String name, String synopsis, String releaseDate, String director, List<String> cast, String classification, int duration) {
        JSONObject movies = (JSONObject) database.get("movies");

        Movie movie = new Movie(name, synopsis, releaseDate, director, cast, true, classification, duration);
        movies.put(name, movie.toJSON());
        database.put("movies", movies);
    }

    public boolean deleteMovie(String name) {
        JSONObject movies = (JSONObject) database.get("movies");
        movies.remove(name);
        database.put("movies", movies);
        return true;
    }

    public void updateName(String oldName, String newName) {
        JSONObject movies = (JSONObject) database.get("movies");

        movies.put(newName, movies.get(oldName));
        movies.remove(oldName);
        database.put("movies", movies);
    }

    public void updateSynopsis(String name, String synopsis) {
        JSONObject movies = (JSONObject) database.get("movies");
        JSONObject movie = (JSONObject) movies.get(name);

        movie.put("synopsis", synopsis);
    }

    public void updateDirector(String name, String director) {
        JSONObject movies = (JSONObject) database.get("movies");
        JSONObject movie = (JSONObject) movies.get(name);

        movie.put("director", director);
    }

    public void updateReleaseDate(String name, String releaseDate) {
        JSONObject movies = (JSONObject) database.get("movies");
        JSONObject movie = (JSONObject) movies.get(name);

        movie.put("release_date", releaseDate);
    }

    public void updateClassification(String name, String classification) {
        JSONObject movies = (JSONObject) database.get("movies");
        JSONObject movie = (JSONObject) movies.get(name);

        movie.put("classification", classification);
    }

    public void updateCast(String name, List<String> newCast) {
        JSONObject movies = (JSONObject) database.get("movies");
        JSONObject movie = (JSONObject) movies.get(name);

        movie.put("cast", newCast);
    }

    public void updateDuration(String name, int duration) {
        JSONObject movies = (JSONObject) database.get("movies");
        JSONObject movie = (JSONObject) movies.get(name);

        movie.put("duration", duration);
    }

    public void updateCinemaDatabase() {
        try (FileWriter writer = new FileWriter(databaseFile)) {
            String noIndent = database.toJSONString();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonElement element = JsonParser.parseString(noIndent);
            String indentedString = gson.toJson(element);
            writer.write(indentedString);
            writer.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JSONObject getDatabase() {
        return database;
    }
}
