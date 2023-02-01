import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;

public class CinemaDatabaseTest {
    private cinemaDatabase database = new cinemaDatabase();

    @Test
    public void addMovieTimeTest() {
        assertTrue(database.addMovieTime("Hoyts Thatswood", "Lord of the Circles", "12/12/2021","12:30", "14:30", "14", "Bronze", "0", "200"));
    }
}
