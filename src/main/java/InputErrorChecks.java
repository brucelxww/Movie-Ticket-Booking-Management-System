import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class InputErrorChecks {
    /**
     * checks if the input is an integer and falls between the lower and upperbound
     * @param input, the user input
     * @param lowerBound, the lower bound for the options
     * @param upperBound, the upper bound for the options
     * @return, true if the user input is an integer and falls within the bound, false otherwise
     */
    public boolean validOptionChecker(String input, int lowerBound, int upperBound) {
        int validInt;
        try {
            validInt = Integer.parseInt(input);
        }

        catch (NumberFormatException e) {
            System.out.println("\nPlease enter one of the options provided.");
            return false;
        }

        if (validInt < lowerBound || validInt > upperBound) {
            System.out.println("\nPlease enter one of the options provided.");
            return false;
        }

        return true;
    }

    /**
     * Checks if the input string is a valid time
     * @param strTime, the string time
     * @return, true if it's a valid time false otherwise
     */
    public boolean timeChecker(String strTime) {
        if (strTime.trim().equals("")) {
            System.out.println("\nPlease enter a time.");
            return false;
        }

        else {
            LocalTime now = LocalTime.now();
            LocalTime time;
            try{
                time = LocalTime.parse(strTime);
            }

            catch (DateTimeParseException | NullPointerException e) {
                System.out.println("\nInvalid time.");
                return false;
            }

            return true;
        }
    }

    /**
     * Checks if the input string is a date
     * @param strDate, the string date
     * @return, true if it is a valid date and is later than the current date, false otherwise
     */
    public boolean dateChecker(String strDate) {
        if (strDate.trim().equals("")) {
            System.out.println("\nPlease enter a date.");
            return false;
        }

        else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            dateFormat.setLenient(false);
            Date now = new Date();
            Date date;
            try {
                date = dateFormat.parse(strDate);

            }

            catch (ParseException e) {
                System.out.println("\nInvalid date.");
                return false;
            }

            if (date.after(now)) {
                return true;
            }

            System.out.println("\nPlease enter a date that is after today's date.");
            return false;
        }
    }

    public boolean giftCardCheck(String cardNumber, CardSystem cardSystem) {
        long cardNum;
        if (cardNumber.length() < 16) {
            System.out.println("\nGift card number cannot be less than 16 digits.");
            return false;
        }

        else if (cardNumber.length() > 16) {
            System.out.println("\nGift card number cannot be greater than 16 digits.");
            return false;
        }

        else {
            try {
                cardNum = Long.parseLong(cardNumber);
            }

            catch (NumberFormatException e) {
                System.out.println("\nPlease enter 16 digits.");
                return false;
            }
        }

        if (cardNum < 0) {
            System.out.println("\nGift card number cannot be negative.");
            return false;
        }

        else if (cardSystem.check_gift_card_exists(cardNumber + "GC")) {
            System.out.println("\nGift card already exist.");
            return false;
        }

        return true;
    }

    public boolean numberChecker(String input) {
        double test;
        try {
            test = Double.parseDouble(input);
        }

        catch (NumberFormatException e) {
            System.out.println("\nPlease enter an number.");
            return false;
        }

        if (test <= 0) {
            System.out.println("Please enter a positive number.");
            return false;
        }

        return true;
    }

    public boolean classificationChecker(String input) {
        ArrayList<String> validClassifications = new ArrayList<>(Arrays.asList(
            "G",
            "PG",
            "M",
            "MA15+",
            "R18+"
        ));

        if (!validClassifications.contains(input)) {
            System.out.println("\nYou must enter a valid classification. Valid classifications are: G, PG, M, MA15+, and R18+.");
            return false;
        }

        return true;
    }

    public boolean nonEmpty(String input) {
        if (input.trim().length() == 0) {
            System.out.println("\nYou must enter text.");
            return false;
        }

        return true;
    }

    public boolean confirmChecker(String input) {
        input = input.trim().toLowerCase();
        if (!input.equals("yes") && !input.equals("cancel")) {
            System.out.println("\nYou must enter either \"yes\" or \"cancel\".");
            return false;
        }

        return true;
    }
}
