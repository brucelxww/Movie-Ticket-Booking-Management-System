import java.io.Console;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.List;

public class InputHandler {
    private userDatabase database = new userDatabase();
    private UserRegisterLogin registerLogin = new UserRegisterLogin(database);
    private InformationDisplayer informationDisplayer;
    private CardSystem cardSystem;
    private cinemaDatabase cinemas;
    private movieDatabase movies;
    private CinemaSystem cinemaSystem;
    private InputErrorChecks errorChecks;

    private final Duration timeout = Duration.ofSeconds(120);
    private ExecutorService executor = Executors.newSingleThreadExecutor();


    public InputHandler(InformationDisplayer informationDisplayer) {
        this.informationDisplayer = informationDisplayer;
    }

    public void init(userDatabase database, UserRegisterLogin userRegisterLogin, CardSystem cardSystem, cinemaDatabase cinemas, movieDatabase movies) {
        this.database = database;
        this.registerLogin = userRegisterLogin;
        this.cardSystem = cardSystem;
        this.movies = movies;
        this.cinemas = cinemas;
        this.cinemaSystem = new CinemaSystem("src/Cinema.json", "src/Movie.json");
        this.errorChecks = new InputErrorChecks();

        cardSystem.load_credits_cards();
        cardSystem.load_gifts_card();
        cardSystem.load_old_customer();
    }

    /**
     * Main menu of the movie system.
     */
    public void mainMenu() {
        System.out.println("Please choose if you're wishing to proceed as a Customer or Staff:");
        System.out.println("1. Customer");
        System.out.println("2. Staff");
        System.out.println("3. Manager");
        System.out.println("4. Exit");

        Scanner userInput = new Scanner(System.in);

        String input = userInput.nextLine();

        while(!(errorChecks.validOptionChecker(input, 1, 4))) {
            System.out.println("\nPlease enter if you're wishing to proceed as a Customer or Staff:");
            System.out.println("1. Customer");
            System.out.println("2. Staff");
            System.out.println("3. Manager");
            System.out.println("4. Exit");
            input = userInput.nextLine();
        }

        int option = Integer.parseInt(input);

        if (option == 1) {
            customerMenu();
        } else if (option == 2) {
            staffMenu();
        }
        else if (option == 3) {
            managerMenu();
        }
        else if (option == 4) {
            System.exit(0);
        }

    }

    /**
     * Menu for the customer
     */
    public void customerMenu() {

        Scanner userInput = new Scanner(System.in);

        String input;

         do {
            System.out.println("\nPlease choose one of the following options:");
            System.out.println("1. Customer register");
            System.out.println("2. Customer login");
            System.out.println("3. Continue as guest");
            System.out.println("4. Exit");
            input = userInput.nextLine();
        } while (!(errorChecks.validOptionChecker(input, 1, 4)));

        int option = Integer.parseInt(input);

        if (option == 1) {
            registerInputHandler();

        } else if (option == 2) {
            userLoginInputHandler(registerLogin, "customer");
            customerSelectionMenu(userInput, true);
        }

        else if (option == 3) {
            customerSelectionMenu(userInput, false);
        }

        else if (option == 4) {
            System.exit(0);
        }

    }

    /**
     * Menu for the staff
     */
    public void staffMenu() {
        userLoginInputHandler(registerLogin, "staff");

        System.out.println("\nPlease enter one of the following options: ");
        System.out.println("1. Add gift card");
        System.out.println("2. Add showing of a movie");
        System.out.println("3. Generate report");
        System.out.println("4. Add movie to the system");
        System.out.println("5. Delete a movie from the system");
        System.out.println("6. Modify movie data");

        Scanner userInput = new Scanner(System.in);

        String input = userInput.nextLine();
        while (!(errorChecks.validOptionChecker(input, 1, 6))) {
            System.out.println("\nPlease enter one of the following options: ");
            System.out.println("1. Add gift card");
            System.out.println("2. Add showing of a movie");
            System.out.println("3. Generate report");
            System.out.println("4. Add movie to the system");
            System.out.println("5. Delete a movie from the system");
            System.out.println("6. Modify movie data");
            input = userInput.nextLine();
        }

        int option = Integer.parseInt(input);

        if (option == 1) {
            addGiftCard(userInput);
        } else if (option == 2) {
            addMovieShowing(userInput);
        }

        else if (option == 3) {
            generateStaffReport(userInput);
        }

        else if (option == 4) {
            addMovie(userInput);
        }

        else if (option == 5) {
            deleteMovie(userInput);
        }

        else if (option == 6) {
            modifyMovieData(userInput);
        }
    }

    /**
     * Menu for the manager
     */
    public void managerMenu() {
        userLoginInputHandler(registerLogin, "manager");

        Scanner userInput = new Scanner(System.in);
        String username = null;
        String password = null;

        String input;
         do {
            System.out.println("\nPlease choose one of the following options:");
            System.out.println("1. Add staff");
            System.out.println("2. Delete staff");
            System.out.println("3. Obtain cancelled transaction report");
            input = userInput.nextLine();
        } while (!(errorChecks.validOptionChecker(input, 1, 3)));

        int option = Integer.parseInt(input);

        if (option == 1) {
            System.out.println("Enter staff name to add to database: ");

            username = userInput.nextLine();

            System.out.println("Enter staff password: ");

            password = userInput.nextLine();

            database.addStaff(username, password);

        } else if (option == 2) {
            System.out.println("\nEnter staff name to delete from the database: ");

            username = userInput.nextLine();

            while (registerLogin.UsernameCheck(username, "staff")) {
                System.out.println("\nUsername does not exist.");
                System.out.println("\nEnter staff name to delete from the database: ");
                username = userInput.nextLine();
            }

            database.removeStaff(username);

        }

        else if (option == 3) {
            System.out.println("\nReport created.");
            System.out.println("Please navigate to the managerReport folder for the report.");
        }

    }

    /**
     * Customer menu for choosing what they want to do
     * @param userInput, the Scanner
     * @param isCustomer, if it is a customer or a guest
     */
    public void customerSelectionMenu(Scanner userInput, boolean isCustomer) {
        int option;

        String input;

        do {
            System.out.println("\nWhat is it that you want to do?:");
            System.out.println("1. Search for specific movie");
            System.out.println("2. Search for specific cinema");
            System.out.println("3. Show all movies");
            System.out.println("4. Show all cinemas");
            System.out.println("5. Save card detail");
            System.out.println("6. Buy tickets");
            System.out.println("7. Exit");
            input = userInput.nextLine();
        } while (!(errorChecks.validOptionChecker(input, 1, 7)));

        option = Integer.parseInt(input);

        if (isCustomer) {
            customerOptions(option, userInput, true);
        }

        else {
            customerOptions(option, userInput, false);
        }
    }

    /**
     * Input handler for customer choosing what they want to do
     * @param option, the option they chose
     * @param userInput, the Scanner
     * @param isCustomer, if it is a customer or a guest.
     */
    public void customerOptions(int option, Scanner userInput, boolean isCustomer) {
        if (option == 1){
            System.out.println("Please enter movie name: ");
            String movieInput = userInput.nextLine();
            informationDisplayer.printMovieInfo(movieInput);
            informationDisplayer.printCinemaAiringForMovie(movieInput);
            customerSelectionMenu(userInput, isCustomer);
        }
        else if(option == 2){
            System.out.println("Please enter cinema name: ");
            String cinemaInput = userInput.nextLine();
            informationDisplayer.printCinemaAirings(cinemaInput);
            customerSelectionMenu(userInput, isCustomer);
        }
        else if(option == 3){
            int movieInput;
            String input;
            do {
                System.out.println("\nPlease select a movie. \n");
                informationDisplayer.printMovieList();
                input = userInput.nextLine();
            } while (!(errorChecks.validOptionChecker(input, 1, cinemaSystem.getMovies().size())));

            movieInput = Integer.parseInt(input) - 1;
            informationDisplayer.printMovieInfo(cinemaSystem.getMovies().get(movieInput).getName());
            informationDisplayer.printCinemaAiringForMovie(cinemaSystem.getMovies().get(movieInput).getName());
            customerSelectionMenu(userInput, isCustomer);
        }
        else if (option == 4){
            int cinemaInput;
            String input;
            do {
                System.out.println("\nPlease select a cinema. \n");
                informationDisplayer.printCinemaList();
                input = userInput.nextLine();
            } while (!(errorChecks.validOptionChecker(input, 1, cinemaSystem.getCinemas().size())));

            cinemaInput = Integer.parseInt(input) - 1;
            informationDisplayer.printCinemaAirings(cinemaSystem.getCinemas().get(cinemaInput).getName());
            customerSelectionMenu(userInput, isCustomer);
        }

        else if (option == 5) {
            if (isCustomer) {
                saveCard();
                customerSelectionMenu(userInput, isCustomer);
            }

            else {
                System.out.println("\nYou need to be logged in to save card details");
                customerMenu();
            }
        }

        else if (option == 6) {
            if (isCustomer) {
                final Future<Boolean> handler = executor.submit(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {

                        return buyingMovieTicket(userInput);
                    }
                });

                try {
                    if (handler.get(timeout.toMillis(), TimeUnit.MILLISECONDS)) {
                        return;
                    }

                    else {
                        customerSelectionMenu(userInput, isCustomer);
                    }

                }

                catch(TimeoutException | InterruptedException | ExecutionException e) {
                    //e.printStackTrace();
                    handler.cancel(true);
                    cancelCheck("cancel", true);
                    System.out.println("\nTransaction timeout.");
                    customerSelectionMenu(new Scanner(System.in), isCustomer);
                }

                return;
            }

            else {
                System.out.println("\nPlease log in first for purchase a ticket.");
                customerMenu();
            }
        }

        else if (option == 7) {
            System.exit(0);
        }
    }

    public void saveCard() {
        Console console = System.console();
        String name = console.readLine("\nPlease enter card name: ");

        while (!cardSystem.cardExits(name)) {
            name = console.readLine("\nPlease enter card name: ");
        }

        char[] password = console.readPassword("\nPlease enter card number: ");

        while (!cardSystem.check_credit_card(name, String.valueOf(password))) {
            System.out.println("Incorrect password.");
            password = console.readPassword("\nPlease enter card number: ");
        }

        System.out.println("\nCard successfully added.");
        cardSystem.add_new_customer(name);
    }

    /**
     * Will check if the user input is the cancel option
     * @param input, the user input
     * @param timeOut, whether or not this cancel is a result of a timeout
     * @return, true if it is a cancel event, false otherwise
     */
    public boolean cancelCheck(String input, boolean timeOut) {
        String username = database.getLoggedIn().get(0).getUsername();

        if (input.equals("cancel")) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();

            try {
                FileWriter writer = new FileWriter("src/managerReport/cancelledTransactions.txt", true);
                if (timeOut) {
                    writer.write("User timeout (name: "+username+"): " + formatter.format(date) + "\n");
                }

                else {
                    writer.write("User cancelled (name: "+username+"): " + formatter.format(date) + "\n");
                }
                writer.close();
            }

            catch (IOException e) {
                e.printStackTrace();
                return false;
            }

            return true;
        }

        return false;
    }

    private double getChildPrice(double basePrice) {
        return 0.6 * basePrice;
    }
    private double getElderlyPensionPrice(double basePrice) {
        return getChildPrice(basePrice);
    }
    private double getStudentPrice(double basePrice) {
        return basePrice * 0.75;
    }
    private int getTotalNumberOfTickets(HashMap<String, Integer> ticketGroups) {
        int total = 0;
        total += ticketGroups.get("adult");
        total += ticketGroups.get("child");
        total += ticketGroups.get("elderly/pension");
        total += ticketGroups.get("student");
        return total;
    }
    private double getTotalPriceFromTicketGroups(HashMap<String, Integer> ticketGroups, double price) {
        double total = 0;
        total += ticketGroups.get("adult") * price;
        total += ticketGroups.get("child") * getChildPrice(price);
        total += ticketGroups.get("elderly/pension") * getElderlyPensionPrice(price);
        total += ticketGroups.get("student") * getStudentPrice(price);
        return total;
    }
    private void printTicketGroupSummary(HashMap<String, Integer> ticketGroups, double price) {
        int groupsPrinted = 0;
        System.out.println("\nSo far you have selected:");

        if (ticketGroups.get("adult") > 0) {
            System.out.println(String.format("%d adult tickets for $%.2f", ticketGroups.get("adult"), ticketGroups.get("adult") * price));
            groupsPrinted++;
        }
        if (ticketGroups.get("child") > 0) {
            System.out.println(String.format("%d child tickets for $%.2f", ticketGroups.get("child"), ticketGroups.get("child") * getChildPrice(price)));
            groupsPrinted++;
        }
        if (ticketGroups.get("student") > 0) {
            System.out.println(String.format("%d student tickets for $%.2f", ticketGroups.get("student"), ticketGroups.get("student") * getStudentPrice(price)));
            groupsPrinted++;
        }
        if (ticketGroups.get("elderly/pension") > 0) {
            System.out.println(String.format("%d elderly/pension tickets for $%.2f", ticketGroups.get("elderly/pension"), ticketGroups.get("elderly/pension") * getElderlyPensionPrice(price)));
            groupsPrinted++;
        }

        if (groupsPrinted > 1) {
            System.out.println(String.format("In total you have selected %d tickets for $%.2f", getTotalNumberOfTickets(ticketGroups), getTotalPriceFromTicketGroups(ticketGroups, price)));
        }
    }

    public boolean processTicketGroups(Scanner userInput, HashMap<String, Integer> ticketGroupMap, double price) {
        double childPrice = getChildPrice(price);
        double elderlyPensionPrice = getElderlyPensionPrice(price);
        double studentPrice = getStudentPrice(price);

        String optionString = null;
        boolean purchasedSome = false;
        boolean done = false;

        while (!done) {
            optionString = null;
            while (optionString == null || !(errorChecks.validOptionChecker(optionString, 1, 4))) {
                if (purchasedSome) {
                    printTicketGroupSummary(ticketGroupMap, price);
                }

                System.out.println("\nPlease select one of the following groups to purchase tickets for (type \"continue\" once done, or type \"cancel\" to cancel transaction): ");
                System.out.println("1. Adult");
                System.out.println("2. Child");
                System.out.println("3. Student");
                System.out.println("4. Senior/pensioner");
                optionString = userInput.nextLine().trim();

                if (optionString.toLowerCase().equals("continue")) {
                    if (purchasedSome) {
                        done = true;
                        break;
                    } else {
                        System.out.println("\nYou must purchase at least one ticket, or cancel the transaction.");
                        optionString = null;
                        continue;
                    }
                }

                if (cancelCheck(optionString, false)) {
                    return false;
                }
            }
            if (done) {
                break;
            }

            int option = Integer.parseInt(optionString);

            if (option == 1) {
                System.out.println(String.format("\nAdult tickets cost $%.2f each.", price));
                String input;
                boolean cancelled = false;
                do {
                    System.out.println("How many would you like to purchase? (or type \"cancel\" to go back)");
                    input = userInput.nextLine().trim();
                    if (input.toLowerCase() == "cancel") {
                        cancelled = true;
                        break;
                    }
                } while (!(errorChecks.numberChecker(input)));
                if (cancelled) {
                    continue;
                }

                int num = Integer.parseInt(input);
                ticketGroupMap.put("adult", ticketGroupMap.get("adult") + num);

                System.out.println(String.format("Added %d adult tickets for $%.2f.", num, num * price));
                purchasedSome = true;
            } else if (option == 2) {
                System.out.println(String.format("\nChild tickets cost $%.2f each.", childPrice));
                String input;
                boolean cancelled = false;
                do {
                    System.out.println("How many would you like to purchase? (or type \"cancel\" to go back)");
                    input = userInput.nextLine().trim();
                    if (input.toLowerCase() == "cancel") {
                        cancelled = true;
                        break;
                    }
                } while (!(errorChecks.numberChecker(input)));
                if (cancelled) {
                    continue;
                }

                int num = Integer.parseInt(input);
                ticketGroupMap.put("child", ticketGroupMap.get("child") + num);

                System.out.println(String.format("Added %d child tickets for $%.2f.", num, num * childPrice));
                purchasedSome = true;
            } else if (option == 3) {
                System.out.println(String.format("\nStudent tickets cost $%.2f each.", studentPrice));
                String input;
                boolean cancelled = false;
                do {
                    System.out.println("How many would you like to purchase? (or type \"cancel\" to go back)");
                    input = userInput.nextLine().trim();
                    if (input.toLowerCase() == "cancel") {
                        cancelled = true;
                        break;
                    }
                } while (!(errorChecks.numberChecker(input)));
                if (cancelled) {
                    continue;
                }

                int num = Integer.parseInt(input);
                ticketGroupMap.put("student", ticketGroupMap.get("student") + num);

                System.out.println(String.format("Added %d student tickets for $%.2f.", num, num * studentPrice));
                purchasedSome = true;
            } else if (option == 4) {
                System.out.println(String.format("\nElderly/pension tickets cost $%.2f each.", elderlyPensionPrice));
                String input;
                boolean cancelled = false;
                do {
                    System.out.println("How many would you like to purchase? (or type \"cancel\" to go back)");
                    input = userInput.nextLine().trim();
                    if (input.toLowerCase() == "cancel") {
                        cancelled = true;
                        break;
                    }
                } while (!(errorChecks.numberChecker(input)));
                if (cancelled) {
                    continue;
                }

                int num = Integer.parseInt(input);
                ticketGroupMap.put("elderly/pension", ticketGroupMap.get("elderly/pension") + num);

                System.out.println(String.format("Added %d elderly/pension tickets for $%.2f.", num, num * elderlyPensionPrice));
                purchasedSome = true;
            }
        }

        return true;
    }

    /**
     * Input handler for buying a ticket
     * @param userInput, the Scanner
     */
    public boolean buyingMovieTicket(Scanner userInput) {
        int option;
        Cinema cinema;
        List<Cinema> cinemaList;


        String input;

        cinemaList = cinemaSystem.getCinemas();

        do {
            System.out.println("\nChoose the cinema you want to go (type \"cancel\" to cancel transaction): ");
            informationDisplayer.printCinemaList();
            input = userInput.nextLine();
            if (cancelCheck(input, false)) {
                return false;
            }
        }while (!(errorChecks.validOptionChecker(input, 1, cinemaList.size())));

        option = Integer.parseInt(input);
        cinema = cinemaList.get(option - 1);

        List<Movie> movieList = cinema.get_all_movies();

         do {
            System.out.println("\nChoose the movie you want to watch (type \"cancel\" to cancel transaction): ");
            informationDisplayer.printRealatedMovieInfo(cinema);
            input = userInput.nextLine();
            if (cancelCheck(input, false)) {
                 return false;
            }
        } while (!(errorChecks.validOptionChecker(input, 1, movieList.size())));

        int movie = Integer.parseInt(input) - 1;
        List<String> screenSizes = cinema.getMovieScreenSizes(movieList.get(movie));

        do {
            System.out.println("\nSelect available screen sizes (type \"cancel\" to cancel transaction): ");
            for (int i = 0; i < screenSizes.size(); i++) {
                System.out.printf("%d. %s\n", i + 1, screenSizes.get(i));
            }
            input = userInput.nextLine();
            if (cancelCheck(input, false)) {
                return false;
            }
        } while (!errorChecks.validOptionChecker(input, 1, screenSizes.size()));

        int screen = Integer.parseInt(input);

        List<String> times = cinema.getMovieStartTimes(movieList.get(movie));
        List<String> date = cinema.getMovieDates(movieList.get(movie));
        List<String> sessPrices = cinema.getMoviePrices(movieList.get(movie));

        do {
            System.out.println("\nPlease select your preferred date and time (type \"cancel\" to cancel transaction): ");
            for (int i = 0; i < times.size(); i++) {
                System.out.printf("%d. Date: %s. Time: %s. Base price: $%.2f\n", i + 1, date.get(i), times.get(i), Double.parseDouble(sessPrices.get(i)));
            }
            input = userInput.nextLine();
            if (cancelCheck(input, false)) {
                return false;
            }
        } while(!errorChecks.validOptionChecker(input, 1, times.size()));

        int timeDate = Integer.parseInt(input);

        /* do {
            System.out.println("\nAdd the number of people (type \"cancel\" to cancel transaction): ");
            input = userInput.nextLine();
            if (cancelCheck(input, false)) {
                 return false;
            }
        } while (!(errorChecks.numberChecker(input)));

        int num = Integer.parseInt(input);*/

        String positions="0";

         do {
            System.out.println("\nChoose your seats postions (type \"cancel\" to cancel transaction)");
            System.out.println("1. front");
            System.out.println("2. middle");
            System.out.println("3. rear");
            input = userInput.nextLine();
            if (cancelCheck(input, false)) {
                 return false;
            }
        } while (!(errorChecks.validOptionChecker(input, 1, 3)));

        option = Integer.parseInt(input);

        if (option == 1){
            positions="front";

        }
        else if (option == 2){
            positions="middle";

        }
        else if (option == 3){
            positions="rear";
        }

        List<String> prices = cinema.getMoviePrices(movieList.get(movie));
        double price = Double.parseDouble(prices.get(0));

        if (screenSizes.get(screen - 1).equals("Silver")) {
            price = price + 2;
        }

        else if (screenSizes.get(screen - 1).equals("Gold")) {
            price = price + 4;
        }

        //price = price * num;
        double basePrice = price;

        HashMap<String, Integer> ticketGroups = new HashMap<>();
        ticketGroups.put("adult", 0);
        ticketGroups.put("child", 0);
        ticketGroups.put("student", 0);
        ticketGroups.put("elderly/pension", 0);

        processTicketGroups(userInput, ticketGroups, basePrice);

        int num = getTotalNumberOfTickets(ticketGroups);
        double totalPrice = getTotalPriceFromTicketGroups(ticketGroups, basePrice);

        if (!paymentHandler(this.cardSystem, totalPrice)) {
            return false;
        }

        System.out.println("\nReceipt");
        System.out.println("Transaction Id: "+informationDisplayer.transactionId());
        System.out.println("Date: "+ date.get(timeDate - 1));
        System.out.println("Time: "+ times.get(timeDate - 1));
        System.out.println("Cinema: "+cinema.getName());
        System.out.println("Movie: "+cinema.get_available_movies().get(movie).getName());
        System.out.println("Screen Size: " + screenSizes.get(screen - 1));
        System.out.printf("Price: %.2f\n", totalPrice);
        System.out.println("People: "+num);
        System.out.println("Seats: "+positions);

        cinemas.updateSeats(cinema.getName(), cinema.get_available_movies().get(movie).getName(), num, timeDate);
        cinemas.updateCinemaDatabase();

        System.exit(0);
        return true;
    }

    // Still need to generate the number of bookings etc
    public void generateStaffReport(Scanner userInput) {
        String cinema;
        List<Cinema> cinemaList = cinemaSystem.getCinemas();

        System.out.println("\nPlease enter cinema you wish to generate the report: ");
        informationDisplayer.printCinemaList();

        cinema = userInput.nextLine();

        while (!(errorChecks.validOptionChecker(cinema, 1, cinemaList.size()))) {
            System.out.println("\nPlease enter cinema you wish to generate the report: ");
            informationDisplayer.printCinemaList();
            cinema = userInput.nextLine();
        }

        int cinemaIndex = Integer.parseInt(cinema) - 1;
        cinema = cinemaList.get(cinemaIndex).getName();

        try {
            FileWriter writer = new FileWriter("src/staffReport/upcoming.txt");
            FileWriter writer2 = new FileWriter("src/staffReport/summary.txt");
            writer.write(informationDisplayer.getCinemaAirings(cinema));
            writer2.write(informationDisplayer.getCinemaSeatNumbers(cinema));
            writer2.close();
            writer.close();

            System.out.println("Please navigate to staffReport to view the reports.");
        }

        catch (IOException e) {
            System.out.println("Error in generating report.");
        }
    }

    public void endSystem() {
        database.updateDatabase();
        cardSystem.update_gift_card_db();
    }

    /**
     * Interface for the staff to add a card to the system
     * @param userInput, the Scanner userInput object
     */
    public void addGiftCard(Scanner userInput) {
        System.out.println("\nPlease enter gift card number: ");

        String cardNumber = userInput.nextLine();

        while (!(errorChecks.giftCardCheck(cardNumber, cardSystem))) {
            System.out.println("\nPlease enter gift card number: ");
            cardNumber = userInput.nextLine();
        }

        System.out.println("Please enter amount to insert into gift card: ");
        String amount = userInput.nextLine();

        while (!(errorChecks.numberChecker(amount))) {
            System.out.println("Please enter amount to insert into gift card: ");
            amount = userInput.nextLine();
        }

        double finalAmount = Double.parseDouble(amount);
        cardNumber = cardNumber + "GC";

        cardSystem.updateOrInsertGiftCard(cardNumber, finalAmount);
    }

    public void addMovieShowing(Scanner userInput) {
        String cinema;
        String movie;
        String date;
        String startTime;
        String endTime;
        String price;

        List<Cinema> cinemaList = cinemaSystem.getCinemas();

        System.out.println("\nPlease enter cinema you wish to show the movie: ");
        informationDisplayer.printCinemaList();

        cinema = userInput.nextLine();

        while (!(errorChecks.validOptionChecker(cinema, 1, cinemaList.size()))) {
            System.out.println("\nPlease enter cinema you wish to show the movie: ");
            informationDisplayer.printCinemaList();
            cinema = userInput.nextLine();
        }

        int cinemaIndex = Integer.parseInt(cinema) - 1;
        cinema = cinemaList.get(cinemaIndex).getName();

        List<Movie> movieList = cinemaList.get(cinemaIndex).get_all_movies();
        System.out.println("\nPlease enter movie you wish to show: ");

        informationDisplayer.printRealatedMovieInfo(cinemaList.get(cinemaIndex));

        movie = userInput.nextLine();

        while (!(errorChecks.validOptionChecker(movie, 1, movieList.size()))) {
            System.out.println("\nPlease enter movie you wish to show: ");
            informationDisplayer.printRealatedMovieInfo(cinemaList.get(cinemaIndex));
            movie = userInput.nextLine();
        }

        int movieIndex = Integer.parseInt(movie) - 1;
        movie = movieList.get(movieIndex).getName();

        System.out.println("\nPlease enter date of showing (DD/MM/YYYY): ");

        date = userInput.nextLine();
        while (!(errorChecks.dateChecker(date))) {
            System.out.println("\nPlease enter date of showing (DD/MM/YYYY): ");
            date = userInput.nextLine();
        }

        System.out.println("\nPlease enter start time of showing in 24 hour format (hh:mm): ");

        startTime = userInput.nextLine();
        while (!(errorChecks.timeChecker(startTime))) {
            System.out.println("\nPlease enter start time of showing in 24 hour format (hh:mm): ");
            startTime = userInput.nextLine();
        }

        System.out.println("\nPlease enter end time of showing in 24 hour format (hh:mm): ");

        endTime = userInput.nextLine();
        while (!(errorChecks.timeChecker(endTime))) {
            System.out.println("\nPlease enter end time of showing in 24 hour format (hh:mm): ");
            endTime = userInput.nextLine();
        }

        String screenSize;
        do {
            System.out.println("\nPlease select the screen size of the movie showing: ");
            System.out.println("1. Bronze");
            System.out.println("2. Silver");
            System.out.println("3. Gold");
            screenSize = userInput.nextLine();
        } while(!(errorChecks.validOptionChecker(screenSize, 1, 3)));

        int size = Integer.parseInt(screenSize);

        if (size == 1) {
            screenSize = "Bronze";
        }

        else if (size == 2) {
            screenSize = "Silver";
        }

        else if (size == 3) {
            screenSize = "Gold";
        }

        System.out.println("\nPlease enter price of the movie: ");

        price = userInput.nextLine();
        while (!(errorChecks.numberChecker(price))) {
            System.out.println("\nPlease enter price of the movie: ");
            price = userInput.nextLine();
        }


        cinemas.addMovieTime(cinema, movie, date, startTime, endTime, price, screenSize, "0" ,"200");

        cinemas.updateCinemaDatabase();

    }

    public String getMovieNameDoesntExist(Scanner userInput, String prompt) {
        String name = null;

        while (name == null || !errorChecks.nonEmpty(name)) {
            System.out.println(prompt);
            name = userInput.nextLine().trim();

            final String finalName = name;
            if (cinemaSystem.getMovies().stream().anyMatch(movie -> movie.getName().equals( finalName))) {
                System.out.println("A movie with that name already exists. Please provide a different name.");
                name = null;
            }
        }

        return name;
    }
    public Movie getMovieExists(Scanner userInput, String prompt) {
        String name = null;
        Movie movie = null;

        while (name == null || !errorChecks.nonEmpty(name)) {
            System.out.println(prompt);
            name = userInput.nextLine().trim();

            for (Movie itrMovie : cinemaSystem.getMovies()) {
                if (itrMovie.getName().equals(name)) {
                    movie = itrMovie;
                    break;
                }
            }

            if (movie == null) {
                System.out.println("\nThere is no movie in the system with that name. Please provide a different name.");
                name = null;
            }
        }

        return movie;
    }
    public String basicGetNonEmptyString(Scanner userInput, String prompt) {
        String value = null;
        while (value == null || !errorChecks.nonEmpty(value)) {
            System.out.println(prompt);
            value = userInput.nextLine().trim();
        }

        return value;
    }
    public List<String> getCast(Scanner userInput, String prompt) {
        List<String> cast = null;
        String castString = null;

        while (cast == null || cast.size() == 0) {
            while (castString == null || !errorChecks.nonEmpty(castString)) {
                System.out.println(prompt);
                castString = userInput.nextLine().trim();
            }

            cast = Stream.of(castString.split(","))
                .map(x -> x.trim())
                .filter(x -> x.length() > 0)
                .collect(Collectors.toList());

            if (cast.size() == 0) {
                System.out.println("You must provide at least one cast member.");
                castString = null;
            }
        }

        return cast;
    }

    public void addMovie(Scanner userInput) {
        String name = null;
        String synopsis = null;
        String releaseDate = null;
        String director = null;
        String classification = null;
        List<String> cast = null;
        int duration = 0;

        name = getMovieNameDoesntExist(userInput, "\nPlease enter the name of the movie to add: ");

        synopsis = basicGetNonEmptyString(userInput, "\nPlease enter the synopsis of the movie: ");

        while (releaseDate == null || !errorChecks.dateChecker(releaseDate)) {
            System.out.println("\nPlease enter the release date of the movie (DD/MM/YYYY): ");
            releaseDate = userInput.nextLine().trim();
        }

        director = basicGetNonEmptyString(userInput, "\nPlease enter the director of the movie: ");

        cast = getCast(userInput, "\nPlease enter the cast of the movie, separated by commas: ");

        while (classification == null || !errorChecks.classificationChecker(classification)) {
            System.out.println("\nPlease enter the classification of the movie: ");
            classification = userInput.nextLine().trim();
        }

        String durationString = null;
        while (durationString == null || !errorChecks.numberChecker(durationString)) {
            System.out.println("\nPlease enter the duration of the movie in hours: ");
            durationString = userInput.nextLine().trim();
        }
        duration = Integer.parseInt(durationString);

        movies.addMovie(name, synopsis, releaseDate, director, cast, classification, duration);
        movies.updateCinemaDatabase();
    }

    private boolean parseConfirm(String input) {
        input = input.trim().toLowerCase();

        if (input.equals("yes")) {
            return true;
        } else {
            return false;
        }
    }

    public void deleteMovie(Scanner userInput) {
        Movie movie = getMovieExists(userInput, "\nPlease enter the name of the movie to delete: ");
        String name = movie.getName();

        System.out.println("\nThis will delete the movie and all of its showings from the system.");

        String confirmString = null;
        while (confirmString == null || !errorChecks.confirmChecker(confirmString)) {
            System.out.println("Are you sure you want to continue? (yes/cancel): ");
            confirmString = userInput.nextLine().trim();
        }

        if (parseConfirm(confirmString)) {
            movies.deleteMovie(name);
            movies.updateCinemaDatabase();
            cinemaSystem.deleteMovie(movie);
            cinemas.deleteMovie(name);
            cinemas.updateCinemaDatabase();

            System.out.println("\n The movie was deleted.");
        }
    }

    public void modifyMovieData(Scanner userInput) {
        Movie movie = getMovieExists(userInput, "\nPlease enter the name of the movie to modify: ");
        String name = movie.getName();

        String optionString = null;
        while (optionString == null || !(errorChecks.validOptionChecker(optionString, 1, 7))) {
            System.out.println("\nPlease enter one of the following options to modify: ");
            System.out.println("1. Name");
            System.out.println("2. Synopsis");
            System.out.println("3. Director");
            System.out.println("4. Release date");
            System.out.println("5. Cast");
            System.out.println("6. Classification");
            System.out.println("7. Duration");
            optionString = userInput.nextLine();
        }
        int option = Integer.parseInt(optionString);

        if (option == 1) {
            String newName = getMovieNameDoesntExist(userInput, "\nPlease enter the new name of the movie: ");

            movie.updateName(newName);
            movies.updateName(name, newName);
            movies.updateCinemaDatabase();
            cinemas.updateMovieName(name, newName);
            cinemas.updateCinemaDatabase();

            System.out.println("\nThe name of the movie has been updated.");
        } else if (option == 2) {
            String newSynopsis = basicGetNonEmptyString(userInput, "\nPlease enter the new synopsis of the movie: ");

            movie.updateSynopsis(newSynopsis);
            movies.updateSynopsis(name, newSynopsis);
            movies.updateCinemaDatabase();

            System.out.println("\nThe synopsis of the movie has been updated.");
        } else if (option == 3) {
            String newDirector = basicGetNonEmptyString(userInput, "\nPlease enter the new director of the movie: ");

            movie.updateDirector(newDirector);
            movies.updateDirector(name, newDirector);
            movies.updateCinemaDatabase();

            System.out.println("\nThe director of the movie has been updated.");
        } else if (option == 4) {
            String releaseDate = null;
            while (releaseDate == null || !errorChecks.dateChecker(releaseDate)) {
                System.out.println("\nPlease enter the new release date of the movie (DD/MM/YYYY): ");
                releaseDate = userInput.nextLine().trim();
            }

            movie.updateReleaseDate(releaseDate);
            movies.updateReleaseDate(name, releaseDate);
            movies.updateCinemaDatabase();

            System.out.println("\nThe release date of the movie has been updated.");
        } else if (option == 5) {
            List<String> newCast = getCast(userInput, "\nPlease enter the new cast of the movie, separated by commas: ");

            movie.updateCast(newCast);
            movies.updateCast(name, newCast);
            movies.updateCinemaDatabase();

            System.out.println("\nThe cast of the movie has been updated.");
        } else if (option == 6) {
            String classification = null;
            while (classification == null || !errorChecks.classificationChecker(classification)) {
                System.out.println("\nPlease enter the new classification of the movie: ");
                classification = userInput.nextLine().trim();
            }

            movie.updateClassification(classification);
            movies.updateClassification(name, classification);
            movies.updateCinemaDatabase();

            System.out.println("The classification of the movie has been updated.");
        } else if (option == 7) {
            String durationString = null;
            while (durationString == null || !errorChecks.numberChecker(durationString)) {
                System.out.println("\nPlease enter the new duration of the movie in hours: ");
                durationString = userInput.nextLine().trim();
            }
            int duration = Integer.parseInt(durationString);

            movie.updateDuration(duration);
            movies.updateDuration(name, duration);
            movies.updateCinemaDatabase();

            System.out.println("The duration of the movie has been updated.");
        }
    }

    //handles user input when they want to register
    public void registerInputHandler() {
        String username;
        char[] password;
        char[] confirmPassword;

        Console input = System.console();
        int count = 3;

        System.out.printf("\nUser Register\n\n");

        //need to do some error checking here
        username = input.readLine("Enter username: ");
        while(registerLogin.UsernameCheck(username, "customer") == false && count > 1) {
            count--;
            System.out.printf("Username already taken, please try again.\n\n");
            username = input.readLine("Enter username: ");
        }

        if (count == 1) {
            return;
        }

        password = input.readPassword("Enter password: ");
        confirmPassword = input.readPassword("Confirm password: ");

        while (!String.valueOf(password).equals(String.valueOf(confirmPassword))) {
            System.out.println("\nPassword does not match.");
            System.out.printf("%s %s\n" , String.valueOf(password), String.valueOf(confirmPassword));
            password = input.readPassword("\nEnter password: ");
            confirmPassword = input.readPassword("Confirm password: ");
        }

        registerLogin.register(username, String.valueOf(password));
        registerLogin.userLogin(username, String.valueOf(password), "customer");

        customerSelectionMenu(new Scanner(System.in), true);
    }

    /**
     * Handles the login and password masking of the login user interface
     * @param user, the customer register login class
     */
    public void userLoginInputHandler(UserRegisterLogin user, String whoIsChecking) {
        String username;
        int count = 3;
        Console console = System.console();
        System.out.print("\n" + whoIsChecking.substring(0, 1).toUpperCase() + whoIsChecking.substring(1) + " Login\n\n");
        username = console.readLine("Enter username: ");

        while (count > 1) {

            if (user.UsernameCheck(username, whoIsChecking) == false) {
                break;
            }

            count--;
            System.out.print("This username does not exist.\n\n");
            username = console.readLine("Enter username: ");
        }

        if (count == 1) {
            return;
        }

        char [] password = console.readPassword("Enter password: ");


        while(!user.userLogin(username, new String(password), whoIsChecking)) {
            System.out.print("Wrong password, try again.\n\n");
            password = console.readPassword("Enter password: ");
        }
    }

    /**
     * Handles the payment inputs of the customer
     * @param cardsystem, the card system
     * @param movie_price, movie prices
     */
    public boolean paymentHandler(CardSystem cardsystem, double movie_price) {
        int count = 3;
        Console console = System.console();
        System.out.println("\nPayment Process");
        String payment_method;
        payment_method = console.readLine("you have 2 payment choices (type \"cancel\" to cancel transaction):\n1. Credit Card\n2. Gift Card\nYou would like to pay with: ");
        if (cancelCheck(payment_method, false)) {
            return false;
        }
        else if (payment_method.equals("1")) {
            while (count > 0) {
                String card_holder = console.readLine("Enter your name (type \"cancel\" to cancel transaction): ");
                if (cancelCheck(card_holder, false)) {
                    return false;
                }
                // check customer's name exists in credit_cards.json file
                if (!cardsystem.check_exist_customer(card_holder)) {
                    if (count > 1) {
                        System.out.println("invalid name, try again");
                    }
                    count--;
                }  // if he/she is a new customer, which means we do not record his/her card number yet, ask the card number
                else if (!cardsystem.check_old_customer(card_holder)) {
                    char[] cardNum = console.readPassword("Enter the card number: ");
                    String input_card_num = String.valueOf(cardNum);
                    String card_number = String.valueOf(cardNum);
                    if (cardsystem.check_credit_card(card_holder, card_number)) {
                        System.out.println("Payment successful, thank you!\n");
                        // add the new customer to the card system so he/she will not need to type the card number next time
                        String saveDetail = console.readLine("Would you like to save your credit card details in our cinema?\n1. Yes \n2. No \n Your choice is: ");
                        if (saveDetail.equals("1")) {
                            cardsystem.add_new_customer(card_holder + "\n");
                        }
                        return true;

                    } else {
                        System.out.println("Card Holder and(or) Card Number do not match, try again");
                        count--;
                    }
                }

                // if he/she is an old customer, it is unnecessary to ask their card number again
                else {
                    System.out.println("Payment successful, thank you!\n");
                    return true;
                }
            }
        }
        else if (payment_method.equals("2")) {
            while (count > 0) {
                String gift_card_number = console.readLine("Enter the gift card number (type \"cancel\" to cancel transaction): ");
                if (cancelCheck(gift_card_number, false)) {
                    return false;
                }
                if (cardsystem.check_gift_card(gift_card_number)) {
                    double amount = cardsystem.get_card_amount(gift_card_number);
                    if (amount < movie_price) {
                        System.out.println("this gift card does not have sufficient fund, you need pay the rest using a credit card.");
                        double rest_amount = movie_price - amount;
                        int credit_card_count = 3;
                        String card_holder;
                        String card_number;
                        // when the gift card has no sufficient value, pay the rest using a credir card
                        while (credit_card_count > 0) {
                            card_holder = console.readLine("Enter your name (type \"cancel\" to cancel transaction): ");
                            if (cancelCheck(card_holder, false)) {
                                return false;
                            }
                            if (!cardsystem.check_exist_customer(card_holder)) {
                                if (count > 1) {
                                    System.out.println("invalid credit card holder name, try again");
                                }
                                credit_card_count--;
                            } else {
                                if (cardsystem.check_old_customer(card_holder)) {
                                    System.out.println("Payment successful, thank you!\n");
                                    return true;
                                } else {
                                    char[] cardNum = console.readPassword("Enter the card number: ");
                                    String input_card_num = String.valueOf(cardNum);
                                    if (cancelCheck(input_card_num, false)) {
                                        return false;
                                    }
                                    card_number = String.valueOf(cardNum);
                                    if (cardsystem.check_credit_card(card_holder, card_number)) {
                                        cardsystem.redeemCard(gift_card_number);
                                        System.out.println("Payment successful, thank you!\n");
                                        String saveDetail = console.readLine("Would you like to save your credit card details in our cinema?\n1. Yes \n2. No \n Your choice is: ");
                                        if (saveDetail.equals("1")){
                                            cardsystem.add_new_customer(card_holder + "\n");
                                        }
                                        return true;
                                    } else {
                                        System.out.println("Card Holder and(or) Card Number do not match, try again");
                                        credit_card_count--;
                                    }
                                }
                            }
                        }
                        if (credit_card_count == 0) {
                            count --;
                        }


                    } else {
                        // redeem that card
                        cardsystem.redeemCard(gift_card_number);
                        System.out.println("Payment successful, thank you!");
                        return true;
                    }
                } else {
                    if (count > 1) {
                        System.out.println("invalid gift card number, try again");
                    }
                    count--;
                }
            }
        } else {
            count--;
            System.out.println("Sorry, we only accept payment by credit card or gift card or both.");
        }
        if (count == 0) {
            System.out.println("Transaction Refused: you have failed to provide valid data for three times");
            return false;
        }
        return false;
    }
}
