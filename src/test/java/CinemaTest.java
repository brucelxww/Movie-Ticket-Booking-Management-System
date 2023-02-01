import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class CinemaTest {
    private Cinema cinema;
    private Movie movieAvail;
    private Movie movieNotAvail;
    private List<String> Gump_cast;
    private HashMap<Movie, List<List<String>>> cinemaMovies;
    private List<Movie> availMovies;
    private List<String> movieAiringDetailsAvail;
    private List<String> movieAiringDetailsNotAvail;
    private List<List<String>> AiringDetailsAvailList;
    private List<List<String>> AiringDetailsNotAvailList;
    private cinemaDatabase database1;
    private movieDatabase database2;
    private List<String> movieDates;
    private List<String> movieStartTimes;
    private List<String> movieEndTimes;
    private List<String> moviePrices;
    private List<String> movieScreenSizes;
    private List<String> movieBookings;
    private List<String> movieAvailSeats;


    @BeforeEach
    public void setup() {
        Gump_cast = new ArrayList<>();
        Gump_cast.add("Tom Hanks");
        Gump_cast.add("Robin Wright");
        movieAvail = new Movie("Forrest Gump","story about gump", "10/10/1995", "Robert Zemeckis", Gump_cast, true, "G", 2);
        movieNotAvail = new Movie("Forrest Chump","story about gump", "10/10/1995", "Robert Zemeckis", Gump_cast, false, "G", 2);
        cinemaMovies = new HashMap<Movie, List<List<String>>>();
        movieAiringDetailsAvail = new ArrayList<>();
        movieAiringDetailsNotAvail = new ArrayList<>();
        movieAiringDetailsAvail.add("26/12/2001");
        movieAiringDetailsAvail.add("2:00pm");
        movieAiringDetailsAvail.add("5:00pm");
        movieAiringDetailsAvail.add("16:00");
        movieAiringDetailsAvail.add("Bronze");
        movieAiringDetailsAvail.add("0");
        movieAiringDetailsAvail.add("100");
        movieAiringDetailsNotAvail.add("26/12/2022");
        movieAiringDetailsNotAvail.add("12:00am");
        movieAiringDetailsNotAvail.add("3:00pm");
        movieAiringDetailsNotAvail.add("16:00");
        movieAiringDetailsNotAvail.add("Silver");
        movieAiringDetailsNotAvail.add("1");
        movieAiringDetailsNotAvail.add("200");
        AiringDetailsAvailList = new ArrayList<>();
        AiringDetailsNotAvailList = new ArrayList<>();
        AiringDetailsAvailList.add(movieAiringDetailsAvail);
        AiringDetailsNotAvailList.add(movieAiringDetailsNotAvail);
        cinemaMovies.put(movieAvail, AiringDetailsAvailList);
        cinemaMovies.put(movieNotAvail, AiringDetailsNotAvailList);
        cinema = new Cinema(cinemaMovies, "cinema name");
        database1 = new cinemaDatabase();
        database2 = new movieDatabase();

        availMovies = new ArrayList<>();
        availMovies.add(movieAvail);

        movieDates = new ArrayList<>();
        movieStartTimes = new ArrayList<>();
        movieEndTimes = new ArrayList<>();
        moviePrices = new ArrayList<>();
        movieScreenSizes = new ArrayList<>();
        movieBookings = new ArrayList<>();
        movieAvailSeats = new ArrayList<>();
        movieDates.add("26/12/2001");
        movieStartTimes.add("2:00pm");
        movieEndTimes.add("5:00pm");
        moviePrices.add("16:00");

        movieScreenSizes.add("Bronze");
        movieBookings.add("0");
        movieAvailSeats.add("100");
    }

    @AfterEach
    public void end() {
        database1.updateCinemaDatabase();
        database2.updateCinemaDatabase();
    }

    @Test
    public void test_getMovies() {
        assertEquals(cinemaMovies, cinema.getMovies(), "test_getMovies failed");
    }

    @Test
    public void test_getAvailableMovies() {
        assertEquals(availMovies, cinema.get_available_movies(), "test_getAvailableMovies failed");
    }

    @Test
    public void test_get_movie_classification() {
        assertEquals(cinema.get_movie_classification(movieAvail), "G", "test_get_movie_classification failed");
    }

    @Test
    public void test_getMovieDates() {
        assertEquals(cinema.getMovieDates(movieAvail), movieDates, "test_getMovieDate failed");
    }

    @Test
    public void test_getMovieStartTimes() {
        assertEquals(cinema.getMovieStartTimes(movieAvail), movieStartTimes, "test_getStartDate failed");
    }

    @Test
    public void test_getMovieEndTimes() {
        assertEquals(cinema.getMovieEndTimes(movieAvail), movieEndTimes, "test_getMovieEndTime failed");
    }

    @Test
    public void test_getMoviePrices() {
        assertEquals(cinema.getMoviePrices(movieAvail), moviePrices, "test_getMovieEndTime failed");
    }

    @Test
    public void test_getMovieScreenSizes() {
        assertEquals(cinema.getMovieScreenSizes(movieAvail), movieScreenSizes);
    }

    @Test
    public void test_getMovieNumberBookings() {
        assertEquals(cinema.getMovieNumberBookings(movieAvail), movieBookings);
    }

    @Test
    public void test_getMovieAvailableSeats() {
        assertEquals(cinema.getMovieAvailableSeats(movieAvail), movieAvailSeats);
    }

    @Test
    public void test_getName() {
        assertEquals(cinema.getName(), "cinema name", "test_getName failed");
    }

    @Test
    public void test_get_all_movies() {
        List<Movie> movies = cinema.get_all_movies();

    }
}
