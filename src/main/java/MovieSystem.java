import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MovieSystem {
    private String name;
    private String synopsis;
    private String release_date;
    private String director;
    private ArrayList<String> cast;
    boolean available;
    private String classification;
    private HashMap<String, HashMap> movieJSON = new HashMap<>();
    private ArrayList<Movie> movieList = new ArrayList<>();
    private int duration;

    public MovieSystem(String movieFile){
        JSONParser jsonParser = new JSONParser();
        try {
            FileReader readerMovie = new FileReader(movieFile);

            // Read movie JSON to create movies
            Object objMovie = jsonParser.parse(readerMovie);
            JSONObject JSONobjMovie = (JSONObject) objMovie;
            movieJSON = (HashMap<String, HashMap>) JSONobjMovie.get("movies");
            for (String movieName : movieJSON.keySet()) {
                synopsis = (String) movieJSON.get(movieName).get("synopsis");
                release_date = (String) movieJSON.get(movieName).get("release_date");
                director = (String) movieJSON.get(movieName).get("director");
                cast = (ArrayList<String>) movieJSON.get(movieName).get("cast");
                available = (boolean) movieJSON.get(movieName).get("available");
                classification = (String) movieJSON.get(movieName).get("classification");
                duration = (int)(long) movieJSON.get(movieName).get("duration");
                movieList.add(new Movie(movieName, synopsis, release_date, director, cast, available, classification, duration));
            }
        }
        catch (ParseException | IOException e){
            e.printStackTrace();
        }
    }

    public List<Movie> getMovies(){
        return movieList;
    }
}
