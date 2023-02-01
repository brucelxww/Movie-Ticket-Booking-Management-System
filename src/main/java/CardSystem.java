import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class CardSystem {
    private final String credit_card_db = "credit_cards.json";
    private HashMap<String, String> card_name_map;
    private final String gift_card_db = "giftCards.json";
    private JSONObject giftCardDB;
    private final String old_customer = "old_customer.txt";
    private ArrayList<String> old_customers;

    public CardSystem(){
        load_gifts_card();
        load_old_customer();
        load_credits_cards();

    }

    public void load_credits_cards() {
        this.card_name_map = new HashMap<>();
        JSONParser jsonParser = new JSONParser();
        try {
            FileReader reader_credit_cards = new FileReader(credit_card_db);
            JSONArray all_cards = (JSONArray) jsonParser.parse(reader_credit_cards);
            for (Object each_card : all_cards) {
                JSONObject card = (JSONObject) each_card;
                String holder_name = (String) card.get("name");
                String card_number = (String) card.get("number");
                card_name_map.put(holder_name, card_number);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public boolean check_exist_customer(String customerName) {
        return this.card_name_map.keySet().contains(customerName);
    }

    public void load_old_customer(){
        this.old_customers = new ArrayList<>();
        try{
            File file = new File(old_customer);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String each_old_customer;
            while ((each_old_customer = br.readLine()) != null) {
                old_customers.add(each_old_customer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void add_new_customer(String customer) {
        try {
            old_customers.add(customer);
            FileWriter writer = new FileWriter(old_customer, true);
            writer.write(customer);
            if (!(writer == null)) {
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean check_old_customer(String customer) {
        return old_customers.contains(customer);
    }
//
//    public boolean check_old_friend(String customer_name) {
//        return old_friends.contains(customer_name);
//    }
//
//    public void add_new_friend(String customer_name) {
//        if (!old_friends.contains(customer_name)) {
//            old_friends.add(customer_name);
//        }
//    }

    public boolean check_credit_card(String owner, String card_number) {
        return this.card_name_map.get(owner).equals(card_number);
    }

    public boolean cardExits(String name) {
        String exists = this.card_name_map.get(name);

        if (exists == null) {
            System.out.println("Card name does not exist.");
            return false;
        }

        else {
            return true;
        }
    }



//----------------------------------------------------------------------//
//---------------------Below is for gift card-------------------------//
//----------------------------------------------------------------------//

    /**
    read the gift card database file and stores it in the giftCardDB database
     */
    public void load_gifts_card() {
        JSONParser parser = new JSONParser();
        try (FileReader jsonReader = new FileReader(gift_card_db)) {
            Object obj = parser.parse(jsonReader);
            giftCardDB = (JSONObject) obj;
        }
        catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * check whether a gift card is existing in the gift database
     * @param card_number the card number of the card we are checking
     * @return true if yes, false otherwise
     */
    public boolean check_gift_card(String card_number) {
        if (giftCardDB == null) {
//            System.out.println("it is null here");
        }
        JSONObject giftCard = (JSONObject) giftCardDB.get(card_number);
        if (check_gift_card_exists(card_number) == false) {
            return false;
        } else if ((double) giftCard.get("redeemable") == 1.0){
            return true;
        } else {
            return false;
        }
    }

    public boolean check_gift_card_exists(String card_number) {
        JSONObject giftCard = (JSONObject) giftCardDB.get(card_number);
        if (giftCard == null) {
            return false;
        }
        else {
            return true;
        }
    }


    /**
     * get the amount of a gift card
     * @param card_number the number of the card we are seeking
     * @return the amount of that gift card
     */
    public double get_card_amount(String card_number) {
        JSONObject giftCard = (JSONObject) giftCardDB.get(card_number);
        return (double) giftCard.get("amount");
    }

    /**
     * Redeems a giftCard
     * @param cardNum, the card number the user inputs (include the GC suffix the user enters)
     * @return the amount of money stored in the gift card, -1 means the redeemCard function failed
     */
    public double redeemCard(String cardNum) {
        JSONObject giftCard = (JSONObject) giftCardDB.get(cardNum);
        if (check_gift_card(cardNum)) {
            System.out.println("Gift card redeemed.");
            updateOrInsertGiftCard(cardNum, 0);
            return (double) giftCard.get("amount");
        }
        else {
            return -1.0;
        }
    }

    /**
     * Updates the gift cards to a redeemed state or adds in a new gift card and reflect the update on database file
     * @param cardNum, card number intened to redeem or insert
     * @param amount, amount of money on inserted gift cars only
     * @return true if gift card does not exist and is successfully added, false if it exists and makes it irredeemable
     */
    public boolean updateOrInsertGiftCard(String cardNum, double amount) {
        JSONObject giftCard = (JSONObject) giftCardDB.get(cardNum);
        if (giftCard == null) {
            JSONObject newCard = new JSONObject();
            newCard.put("redeemable", 1.0);
            newCard.put("amount", amount);
            giftCardDB.put(cardNum, newCard);
            update_gift_card_db();
            return true;
        }
        else {
            giftCard.put("redeemable", 0.0);
            update_gift_card_db();
            return false;
        }

    }

    public boolean update_gift_card_db() {
        try (FileWriter writer = new FileWriter(gift_card_db)) {
            String noIndent = giftCardDB.toJSONString();
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

}
