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
import java.util.Set;

public class cinemaDatabase {
    private final String databaseFile = "src/Cinema.json";

    JSONObject database;

    public cinemaDatabase() {
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

    public boolean updateSeats(String cinema, String movie, int numSeats, int index) {
        JSONObject cinemas = (JSONObject) database.get("cinemas");
        JSONObject theatre = (JSONObject) cinemas.get(cinema);

        JSONArray times = (JSONArray) theatre.get(movie);
        List<String> list = (ArrayList<String>) times.get(index - 1);

        int bookings = Integer.parseInt(list.get(5));
        int seats = Integer.parseInt(list.get(6));
        int bookedSeats = Integer.parseInt(list.get(7));

        list.set(5, String.valueOf(bookings + 1));
        list.set(6, String.valueOf(seats - numSeats));
        list.set(7, String.valueOf(numSeats + bookedSeats));

        times.set(index - 1, list);

        theatre.put(movie, times);
        cinemas.put(cinema, theatre);
        return true;
    }

    public boolean addMovieTime(String cinema, String movie, String date, String startTime, String endTime, String price, String screenSize, String numBooking, String numSeats) {
        JSONObject cinemas = (JSONObject) database.get("cinemas");
        List<String> stuff = new ArrayList<String>();

        System.out.printf("cinema: %s, movie: %s, date: %s, start time: %s, end time: %s, price: %s\n", cinema, movie, date, startTime, endTime, price);
        stuff.add(movie);
        stuff.add(date);
        stuff.add(startTime);
        stuff.add(endTime);
        stuff.add(price);
        stuff.add(screenSize);
        stuff.add(numBooking);
        stuff.add(numSeats);

        JSONObject theatre = (JSONObject) cinemas.get(cinema);
        JSONArray times = (JSONArray) theatre.get(movie);
        times.add(stuff);

        theatre.put(movie, times);
        cinemas.put(cinema, theatre);
        return true;
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

    public void deleteMovie(String name) {
        JSONObject cinemas = (JSONObject) database.get("cinemas");

        for (String cinemaName : (Set<String>) cinemas.keySet()) {
            JSONObject cinema = (JSONObject) cinemas.get(cinemaName);

            if (cinema.containsKey(name)) {
                cinema.remove(name);
                cinemas.put(cinemaName, cinema);
            }

        }
    }

    public void updateMovieName(String oldName, String newName) {
        JSONObject cinemas = (JSONObject) database.get("cinemas");

        for (String cinemaName : (Set<String>) cinemas.keySet()) {
            JSONObject cinema = (JSONObject) cinemas.get(cinemaName);

            if (cinema.containsKey(oldName)) {
                cinema.put(newName, cinema.get(oldName));
                cinema.remove(oldName);
                cinemas.put(cinemaName, cinema);
            }

        }
    }

    /*public JSONObject getDatabase() {
        return database;
    }*/
}
