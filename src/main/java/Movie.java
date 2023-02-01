import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONObject;

public class Movie {
    private String name;
    private String synopsis;
    private String release_date;
    private String director;
    private List<String> cast;
    private boolean available;
    private String classification;
    private int duration;

    public Movie(String name, String synopsis, String release_date, String director, List<String> cast, boolean available, String classification, int duration) {
        this.name = name;
        this.synopsis = synopsis;
        this.release_date = release_date;
        this.director = director;
        this.cast = cast;
        this.available = available;
        this.classification = classification;
        this.duration = duration;
    }

    public String getSynopsis() {
        return this.synopsis;
    }

    public String getRelease_date() {
        return this.release_date;
    }

    public String getDirector() {
        return this.director;
    }

    public List<String> getCast() {
        return this.cast;
    }

    public boolean isAvailable() {
        return this.available;
    }

    public String getClassification() {
        return this.classification;
    }

    public String getName(){
        return this.name;
    }

    public void take_down() {
        this.available = false;
    }

    public void release() {
        this.available = true;
    }

    public int getDuration(){
        return this.duration;
    }

    public JSONObject toJSON() {
        JSONObject data = new JSONObject();
        data.put("duration", duration);
        data.put("cast", cast);
        data.put("release_date", release_date);
        data.put("director", director);
        data.put("name", name);
        data.put("available", available);
        data.put("synopsis", synopsis);
        data.put("classification", classification);

        return data;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public void updateDirector(String director) {
        this.director = director;
    }

    public void updateReleaseDate(String releaseDate) {
        this.release_date = releaseDate;
    }

    public void updateClassification(String classification) {
        this.classification = classification;
    }

    public void updateCast(List<String> cast) {
        this.cast = cast;
    }

    public void updateDuration(int duration) {
        this.duration = duration;
    }
}
