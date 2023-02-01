import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CinemaSystem {

    private MovieSystem movieSystem;
    private List<Movie> movieList;
    private HashMap<Movie, List<List<String>>> movies = new HashMap<>();
    private List<Cinema> cinemas= new ArrayList<>();
    private HashMap<String, HashMap<String, List<List<String>>>> cinemaJSON = new HashMap<>();
    private String cinemaName;

    public CinemaSystem(String cinemaFile, String movieFile) {
        movieSystem = new MovieSystem(movieFile);
        movieList = movieSystem.getMovies();
        JSONParser jsonParser = new JSONParser();
        try {
            FileReader readerCinema = new FileReader(cinemaFile);


            // Read cinema JSON to create cinemas
            Object objCinema = jsonParser.parse(readerCinema);
            JSONObject JSONobjCinema = (JSONObject) objCinema;
            cinemaJSON = (HashMap<String, HashMap<String, List<List<String>>>>) JSONobjCinema.get("cinemas");

            // Creating cinema by taking the movies from CinemaJSON and matching it from the movies in movielist
            for (String cinema: cinemaJSON.keySet()){
                for(String movieName: cinemaJSON.get(cinema).keySet()) {
                    cinemaName = cinema;
                    for (Movie movie : movieList) {
                        if (movieName.equals(movie.getName())) {
                            movies.put(movie, cinemaJSON.get(cinema).get(movieName));
                        }
                    }
                }
                cinemas.add(new Cinema((HashMap<Movie, List<List<String>>>) movies.clone(), cinemaName));
                movies.clear();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public List<Cinema> getCinemas(){
        return cinemas;
    }

    public List<Movie> getMovies(){
        return movieList;
    }

    public void deleteMovie(Movie movie) {
        movieList.remove(movie);

        for (Cinema cinema : cinemas) {
            cinema.deleteMovie(movie);
        }
    }
}
