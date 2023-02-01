import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MovieTest {
    Movie movie;
    List<String> Gump_cast;

    @BeforeEach
    public void setup() {
        Gump_cast = new ArrayList<>();
        Gump_cast.add("Tom Hanks");
        Gump_cast.add("Robin Wright");
        movie = new Movie("Forrest Gump","story about gump", "10/10/1995", "Robert Zemeckis", Gump_cast, true, "G", 2);
    }

    @Test
    public void test_getSynopsis() {
        assertEquals("story about gump", movie.getSynopsis(), "test_getSynopsis fails");
    }

    @Test
    public void test_getRelease_date() {
        assertEquals("10/10/1995", movie.getRelease_date(), "test_getRelease_date fails");
    }

    @Test
    public void test_getDirector() {
        assertEquals("Robert Zemeckis", movie.getDirector(), "test_getDirector fails");
    }

    @Test
    public void test_getCast() {
        List<String> expect_cast = new ArrayList<>();
        expect_cast.add("Tom Hanks");
        expect_cast.add("Robin Wright");
        assertEquals(expect_cast, movie.getCast(), "test_getCast fails");
    }

    @Test
    public void test_isAvailable() {
        assertTrue(movie.isAvailable(), "test_isAvailable fails");
        movie.take_down();
        assertFalse(movie.isAvailable(), "test_isAvailable fails, the available attribute should be false if it is taken down");
        movie.release();
        assertTrue(movie.isAvailable(), "test_isAvailable fails, the available attribute should be true if it is released again");
    }

    @Test
    public void test_getClassification() {
        assertEquals("G", movie.getClassification(), "test_getClassification fails");
    }

    @Test
    public void test_getName() {
        assertEquals("Forrest Gump", movie.getName(), "test_getName fails");
    }

    @Test
    public void test_takedown() {
        movie.take_down();
        assertFalse(movie.isAvailable(), "test_takedown fails");
    }

    @Test
    public void test_get_duration() {
        assertEquals(2, movie.getDuration(), "test_get_duration fails");
    }

    @Test
    public void test_updateName() {
        String name = "abcde";
        movie.updateName(name);
        assertEquals(movie.getName(), name);
    }

    @Test
    public void test_updateSynopsis() {
        String synopsis = "abcde";
        movie.updateSynopsis(synopsis);
        assertEquals(movie.getSynopsis(), synopsis);
    }

    @Test
    public void test_updateDirector() {
        String director = "abcde";
        movie.updateDirector(director);
        assertEquals(movie.getDirector(), director);
    }

    @Test
    public void test_updateCast() {
        ArrayList<String> newCast = new ArrayList<>();
        newCast.add("Test Actor");

        movie.updateCast(newCast);
        assertEquals(movie.getCast(), newCast);
    }

    @Test
    public void test_updateReleaseDate() {
        String releaseDate = "01/01/2000";
        movie.updateReleaseDate(releaseDate);
        assertEquals(movie.getRelease_date(), releaseDate);
    }

    @Test
    public void test_updateClassification() {
        String classification = "G";
        movie.updateClassification(classification);
        assertEquals(movie.getClassification(), classification);
    }

    @Test
    public void test_updateDuration() {
        int duration = 10;
        movie.updateDuration(duration);
        assertEquals(movie.getDuration(), duration);
    }

    @Test
    public void test_toJSON() {
        JSONObject data = new JSONObject();
        data.put("duration", 2);
        data.put("cast", Gump_cast);
        data.put("release_date", "10/10/1995");
        data.put("director", "Robert Zemeckis");
        data.put("name", "Forrest Gump");
        data.put("available", true);
        data.put("synopsis", "story about gump");
        data.put("classification", "G");

        assertEquals(movie.toJSON(), data);
    }
}
