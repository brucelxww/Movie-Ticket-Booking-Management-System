import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

public class Cinema{


    private HashMap<Movie, List<List<String>>> movies;
    private String name;

    public Cinema(HashMap<Movie, List<List<String>>> movies, String name){
        this.name = name;
        this.movies = movies;

    }

    public HashMap<Movie, List<List<String>>> getMovies(){
        return movies;
    }

    public ArrayList<Movie> get_available_movies() {
        ArrayList<Movie> available_movies = new ArrayList<>();
        for (Movie each_movie : movies.keySet()) {
            if (each_movie.isAvailable()) {
                available_movies.add(each_movie);
            }
        }
        return available_movies;
    }

    public ArrayList<Movie> get_all_movies() {
        ArrayList<Movie> all_movies = new ArrayList<>();
        for (Movie each_movie : movies.keySet()) {

            all_movies.add(each_movie);

        }
        return all_movies;
    }

    public String get_movie_classification(Movie movie) {
        return movie.getClassification();
    }

    public List<String> getMovieDates(Movie movie){
        List<String> dates = new ArrayList<>();
        for (List<String> airing: movies.get(movie)){
            dates.add(airing.get(0));
        }
        return dates;
    }

    public List<String> getMovieStartTimes(Movie movie){
        List<String> startTimes = new ArrayList<>();
        for (List<String> airing: movies.get(movie)){
            startTimes.add(airing.get(1));
        }
        return startTimes;
    }

    public List<String> getMovieEndTimes(Movie movie){
        List<String> endTimes = new ArrayList<>();
        for (List<String> airing: movies.get(movie)){
            endTimes.add(airing.get(2));
        }
        return endTimes;
    }

    public List<String> getMoviePrices(Movie movie){
        List<String> moviePrices = new ArrayList<>();
        for (List<String> airing: movies.get(movie)){
            moviePrices.add(airing.get(3));
        }
        return moviePrices;
    }

    public List<String> getMovieScreenSizes(Movie movie){
        List<String> screenSizes = new ArrayList<>();
        for (List<String> airing: movies.get(movie)){
            screenSizes.add(airing.get(4));
        }
        return screenSizes;
    }

    public List<String> getMovieNumberBookings(Movie movie){
        List<String> bookings = new ArrayList<>();
        for (List<String> airing: movies.get(movie)){
            bookings.add(airing.get(5));
        }
        return bookings;
    }

    public List<String> getMovieAvailableSeats(Movie movie){
        List<String> numSeats = new ArrayList<>();
        for (List<String> airing: movies.get(movie)){
            numSeats.add(airing.get(6));
        }
        return numSeats;
    }

    public String getName(){
        return this.name;
    }

    public void deleteMovie(Movie movie) {
        movies.remove(movie);
    }
}

