import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SystemsTest {

    CinemaSystem cinemaSystem;
    MovieSystem movieSystem;


    @BeforeEach
    public void setup(){
        cinemaSystem = new CinemaSystem("src/CinemaTest.json", "src/Movie.json");
        movieSystem =  new MovieSystem("src/Movie.json");

    }

    @Test
    public void test_getMovies(){

    }

    @Test
    public void test_getCinemas(){

    }

}
