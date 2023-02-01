
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Main {

    private ArrayList<Cinema> cinemas = new ArrayList();

    public static void main(String[] args) {
        userDatabase database = new userDatabase();
        UserRegisterLogin customer = new UserRegisterLogin(database);
        CardSystem giftCardDatabase = new CardSystem();
        CinemaSystem cinemaSystem = new CinemaSystem("src/Cinema.json", "src/Movie.json");
        InformationDisplayer informationDisplayer = new InformationDisplayer(cinemaSystem);
        InputHandler inputs = new InputHandler(informationDisplayer);
        UserRegisterLogin registerLogin = new UserRegisterLogin(database);
        CardSystem cardSystem = new CardSystem();
        cinemaDatabase cinemas = new cinemaDatabase();
        movieDatabase movies = new movieDatabase();

//        customer.registerInputHandler();

//        inputs.loginInputHandler(customer);
//        cardSystem.load_gifts_card();
//        database.updateDatabase();

        inputs.init(database, registerLogin, cardSystem, cinemas, movies);
        inputs.mainMenu();
        inputs.endSystem();

//        inputs.addMovieShowing(new Scanner(System.in));


//        cardSystem.updateOrInsertGiftCard("0000000000000000GC", 200.0);
//                                            "1234567890123456GC"
//        giftCardDatabase.updateOrInsertCard("0000000000000000GC");


    }
}

