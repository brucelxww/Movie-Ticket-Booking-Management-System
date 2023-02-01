
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import com.google.gson.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class userDatabase {
    //databese is the whole database
    private JSONObject database;

    //customerDatabase only contains the customer details
    private JSONObject customerDatabase;

    //staffDatabase contains staff details
    private JSONObject staffDatabase;

    //staffDatabase contains staff details
    private JSONObject managerDatabase;

    private final String databaseFile = "userDatabase.json";

    private List<Customer> loggedIn = new ArrayList<>();

    public userDatabase() {
        JSONParser parser = new JSONParser();

        try (FileReader jsonReader = new FileReader(databaseFile)) {
            Object obj = parser.parse(jsonReader);
            database = (JSONObject) obj;
            customerDatabase = (JSONObject) database.get("userDetails");
            staffDatabase = (JSONObject) database.get("staffDetails");
            managerDatabase = (JSONObject) database.get("managerDetails");
        }

        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        catch (IOException e) {
            e.printStackTrace();

        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }

    }



    public JSONObject getCustomerDatabase() {
        return customerDatabase;
    }

    public JSONObject getStaffDatabase() {
        return staffDatabase;
    }

    public JSONObject getManagerDatabase() {
        return managerDatabase;
    }

    public void addCustomer(String username, String password) {
        customerDatabase.put(username, password);
    }

    public void addStaff(String username, String password) {
        staffDatabase.put(username, password);
    }

    public void removeStaff(String username) {
        staffDatabase.remove(username);
    }

    //write the updated database with new users back to file
    public boolean updateDatabase() {
        database.put("userDetails", customerDatabase);
        database.put("staffDetails", staffDatabase);
        database.put("managerDetails", managerDatabase);

        try (FileWriter writer = new FileWriter(databaseFile)) {
            String noIndent = database.toJSONString();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonElement element = JsonParser.parseString(noIndent);
            String indentedString = gson.toJson(element);
            writer.write(indentedString);
            writer.flush();
        }

        catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    public List<Customer> getLoggedIn() {
        return loggedIn;
    }

    public void addLoggedInUser(Customer c) {
        loggedIn.add(c);
    }
}

