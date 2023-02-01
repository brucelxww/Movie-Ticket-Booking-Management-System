import java.util.List;
import java.util.Locale;
import java.util.Set;

public class InformationDisplayer {

    private CinemaSystem cinemaSystem;
    public List<Cinema> cinemas;
    private List<Movie> movieList;
    private List<String> dates;
    private List<String> startTimes;
    private List<String> endTimes;
    private List<String> prices;
    private List<String> screenSizes;
    private List<String> numBookings;
    private List<String> availSeats;


    public InformationDisplayer(CinemaSystem cinemaSystem){
        this.cinemaSystem = cinemaSystem;
        this.cinemas = cinemaSystem.getCinemas();
        this.movieList = cinemaSystem.getMovies();
    }

    public void printAllViewings(){
        for (Cinema cinema : cinemas) {
            Set<Movie> movieSet = cinema.getMovies().keySet();

            if (movieSet.isEmpty()) {
                continue;
            }

            System.out.println("\n\n    "  + cinema.getName());
            System.out.println("    -------------------------------");
            for (Movie movie : movieSet) {
                dates = cinema.getMovieDates(movie);
                startTimes = cinema.getMovieStartTimes(movie);
                endTimes = cinema.getMovieEndTimes(movie);
                prices = cinema.getMoviePrices(movie);
                screenSizes = cinema.getMovieScreenSizes(movie);
                numBookings = cinema.getMovieNumberBookings(movie);
                availSeats = cinema.getMovieAvailableSeats(movie);
                System.out.println("   |" + movie.getName() + "|");
                for (int i = 0; i < startTimes.size(); i++){
                    System.out.println("    " + screenSizes.get(i) + " screen");
                    System.out.println("    " + dates.get(i) + ", " + startTimes.get(i) + " - " + endTimes.get(i));
                    System.out.println("    " + "$" +prices.get(i));
                    System.out.println("    " + availSeats.get(i) + " available seats" + "\n");
                }

            }
        }
    }
    public void printCinemaList(){
        int count=1;
        for (Cinema cinema : cinemas) {
            System.out.println(count + ": " + cinema.getName());
            count=count+1;
        }
    }

    public void printMovieList(){
        int counter = 1;
        for (Movie movie : movieList){
            System.out.println(counter + ". " + movie.getName());
            counter ++;
        }
    }

        public void printCinemaAiringForMovie(String movieName){
        System.out.println("\n\nCinemas showing : " + movieName);
        for (Cinema cinema: cinemas){
                Set<Movie> movieSet = cinema.getMovies().keySet();

                for (Movie movie : movieSet) {
                    if (movie.getName().equalsIgnoreCase(movieName)) {
                        System.out.println("\n" + cinema.getName());
                        dates = cinema.getMovieDates(movie);
                        startTimes = cinema.getMovieStartTimes(movie);
                        endTimes = cinema.getMovieEndTimes(movie);
                        prices = cinema.getMoviePrices(movie);
                        screenSizes = cinema.getMovieScreenSizes(movie);
                        numBookings = cinema.getMovieNumberBookings(movie);
                        availSeats = cinema.getMovieAvailableSeats(movie);
                        for (int i = 0; i < startTimes.size(); i++) {
                            System.out.println("    " + screenSizes.get(i) + " screen");
                            System.out.println("    " + dates.get(i) + ", " + startTimes.get(i) + " - " + endTimes.get(i));
                            System.out.println("    " + "$" + prices.get(i));
                            System.out.println("    " + availSeats.get(i) + " available seats" + "\n");
                        }
                        System.out.println("    -----------------");
                    }
                }
        }
    }

    public void printRealatedMovieInfo(Cinema cinema){
        int count=1;
        for (Movie movie : cinema.get_all_movies()) {
            System.out.println(count + ": " + movie.getName());
            count=count+1;
        }
    }

    public void printAllMovieInfo(){
        for (Movie movie: movieList) {
            System.out.println(
                    movie.getName() +
                            "\n  Synopsis: " + movie.getSynopsis() +
                            "\n  Release date: " + movie.getRelease_date() +
                            "\n  Director: " + movie.getDirector() +
                            "\n  Cast: " + movie.getCast().toString() +
                            "\n  Classification: " + movie.getClassification() +
                            "\n  Duration: " + movie.getDuration() + "\n "
            );
        }
    }

    public Integer transactionId() {
        int id = (int)(Math.random()*90000000 + 10000000);
        return id;
    }

    public void printMovieInfo(String movieName){
        for (Movie movie : movieList){
            if (movie.getName().toLowerCase().equals(movieName.toLowerCase())){
                System.out.println(
                                "\n" + movie.getName() +
                                "\n  Synopsis: " + movie.getSynopsis() +
                                "\n  Release date: " + movie.getRelease_date() +
                                "\n  Director: " + movie.getDirector() +
                                "\n  Cast: " + movie.getCast().toString() +
                                "\n  Classification: " + movie.getClassification() +
                                "\n  Duration: " + movie.getDuration()

                );
                return;
            }
        }
        System.out.println("No such movie exists.");
    }

    public void printCinemaAirings(String cinemaName){
        for (Cinema cinema : cinemas) {
            if (cinema.getName().toLowerCase().equals(cinemaName.toLowerCase())) {
                Set<Movie> movieSet = cinema.getMovies().keySet();

                System.out.println(cinema.getName());

                for (Movie movie : movieSet) {
                    dates = cinema.getMovieDates(movie);
                    startTimes = cinema.getMovieStartTimes(movie);
                    endTimes = cinema.getMovieEndTimes(movie);
                    prices = cinema.getMoviePrices(movie);
                    System.out.println("\n   |" + movie.getName() + "|");
                    for (int i = 0; i < startTimes.size(); i++){
                        System.out.println("    " + dates.get(i) + ", " + startTimes.get(i) + " - " + endTimes.get(i));
                        System.out.println("    " + "$" +prices.get(i) + "\n");
                    }
                    System.out.println("    -----------------");
                }
                return;
            }
        }
        System.out.println("No such cinema exists.");
    }

    public String getCinemaAirings(String cinemaName){
        String result = "";
        for (Cinema cinema : cinemas) {
            if (cinema.getName().toLowerCase().equals(cinemaName.toLowerCase())) {
                Set<Movie> movieSet = cinema.getMovies().keySet();

                for (Movie movie : movieSet) {

                    for (int i = 0; i < cinema.getMovieStartTimes(movie).size(); i++) {
                        result = result + ("\n    Title: " + movie.getName() + "\n");
                        result = result + ("    Date: " + cinema.getMovieDates(movie).get(i) + "\n");
                        result = result + ("    Starting time: " + cinema.getMovieStartTimes(movie).get(i) + "\n");
                        result = result + ("    End time: " + cinema.getMovieEndTimes(movie).get(i) + "\n");
                    }
                }
                return result;
            }
        }
        System.out.println("No such cinema exists.");
        return null;
    }

    public String getCinemaSeatNumbers(String cinemaName){
        String result = "";
        for (Cinema cinema : cinemas) {
            if (cinema.getName().toLowerCase().equals(cinemaName.toLowerCase())) {
                Set<Movie> movieSet = cinema.getMovies().keySet();

                for (Movie movie : movieSet) {

                    for (int i = 0; i < cinema.getMovieStartTimes(movie).size(); i++) {
                        result = result + ("\n    Title: " + movie.getName() + "\n");
                        result = result + ("    Screen size: " + cinema.getMovieScreenSizes(movie).get(i) + "\n");
                        result = result + ("    Date: " + cinema.getMovieDates(movie).get(i) + "\n");
                        result = result + ("    Starting time: " + cinema.getMovieStartTimes(movie).get(i) + "\n");
                        result = result + ("    End time: " + cinema.getMovieEndTimes(movie).get(i) + "\n");
                        result = result + ("    Bookings: " + cinema.getMovieNumberBookings(movie).get(i) + "\n");
                        result = result + ("    Available seats: " + cinema.getMovieAvailableSeats(movie).get(i) + "\n");
                        result = result + ("    Booked seats: " + String.valueOf(200 - Integer.parseInt(cinema.getMovieAvailableSeats(movie).get(i))) + "\n");
                    }
                }
                return result;
            }
        }
        System.out.println("No such cinema exists.");
        return null;
    }
}
