import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.*;

public class MovieDatabaseTest {
    private movieDatabase database;
    ArrayList<String> Gump_cast = new ArrayList<>();
    Movie movie;

    MovieDatabaseTest() {
      Gump_cast.add("Tom Hanks");
      Gump_cast.add("Robin Wright");
      movie = new Movie("Forrest Gump","story about gump", "10/10/1995", "Robert Zemeckis", Gump_cast, true, "G", 2);
    }

    @BeforeEach
    public void setup() {
      database = new movieDatabase();
    }

    @Test
    public void test_add_movie() {

      database.addMovie(movie.getName(), movie.getSynopsis(), movie.getRelease_date(), movie.getDirector(), movie.getCast(), movie.getClassification(), movie.getDuration());

      JSONObject movies = (JSONObject) database.getDatabase().get("movies");
      assertEquals(movies.get("Forrest Gump"), movie.toJSON());
    }

    @Test
    public void test_deleteMovie() {
      database.deleteMovie("Octopus Game");

      JSONObject movies = (JSONObject) database.getDatabase().get("movies");
      assertEquals(movies.get("Octopus Game"), null);
    }

    @Test
    public void test_updateName() {
      JSONObject movies = (JSONObject) database.getDatabase().get("movies");
      JSONObject oldMovie = (JSONObject) movies.get("Octopus Game");

      String newName = "Test Name";
      database.updateName("Octopus Game", newName);

      assertEquals(movies.get("Octopus Game"), null);
      assertEquals(movies.get(newName), oldMovie);
    }

    @Test
    public void test_updateSynopsis() {
      JSONObject movies = (JSONObject) database.getDatabase().get("movies");

      String newSynopsis = "Test";
      database.updateSynopsis("Octopus Game", newSynopsis);

      JSONObject movie = (JSONObject) movies.get("Octopus Game");
      assertEquals(movie.get("synopsis"), newSynopsis);
    }

    @Test
    public void test_updateDirector() {
      JSONObject movies = (JSONObject) database.getDatabase().get("movies");

      String newDirector = "Test";
      database.updateDirector("Octopus Game", newDirector);

      JSONObject movie = (JSONObject) movies.get("Octopus Game");
      assertEquals(movie.get("director"), newDirector);
    }

    @Test
    public void test_updateClassification() {
      JSONObject movies = (JSONObject) database.getDatabase().get("movies");

      String newValue = "G";
      database.updateClassification("Octopus Game", newValue);

      JSONObject movie = (JSONObject) movies.get("Octopus Game");
      assertEquals(movie.get("classification"), newValue);
    }

    @Test
    public void test_updateReleaseDate() {
      JSONObject movies = (JSONObject) database.getDatabase().get("movies");

      String newValue = "01/01/3000";
      database.updateReleaseDate("Octopus Game", newValue);

      JSONObject movie = (JSONObject) movies.get("Octopus Game");
      assertEquals(movie.get("release_date"), newValue);
    }

    @Test
    public void test_updateDuration() {
      JSONObject movies = (JSONObject) database.getDatabase().get("movies");

      int newValue = 10;
      database.updateDuration("Octopus Game", newValue);

      JSONObject movie = (JSONObject) movies.get("Octopus Game");
      assertEquals(movie.get("duration"), newValue);
    }
}
