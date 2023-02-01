import java.util.Scanner;

public class UserRegisterLogin {
    userDatabase database;

    public UserRegisterLogin(userDatabase database) {
        this.database = database;
    }


    public boolean UsernameCheck(String username, String whoIsChecking) {
        String user = null;
        if (whoIsChecking.equals("customer")) {
            user = (String) database.getCustomerDatabase().get(username);
        }

        else if (whoIsChecking.equals("staff")) {
            user = (String) database.getStaffDatabase().get(username);
        }

        else if (whoIsChecking.equals("manager")) {
            user = (String) database.getManagerDatabase().get(username);
        }

        //if the username is not taken
        if (user != null) {

            return false;
        }

        else {
            return true;
        }
    }

    public boolean register(String username, String password) {

        //if the username is not taken
        if (UsernameCheck(username, "customer")) {
            //adds user to database
            database.addCustomer(username, password);
            return true;
        }

        else {
            return false;
        }
    }



    public boolean userLogin(String username, String password, String whoIsChecking) {
        String realPassword = null;
        if (whoIsChecking.equals("customer")) {
            realPassword = (String) database.getCustomerDatabase().get(username);
        }

        else if (whoIsChecking.equals("staff")) {
            realPassword = (String) database.getStaffDatabase().get(username);
        }

        else if (whoIsChecking.equals("manager")) {
            realPassword = (String) database.getManagerDatabase().get(username);
        }


        if (realPassword == null) {
            return false;
        }

        else if (realPassword.equals(password)) {
            System.out.println();
            System.out.println("Successfully logged in!");

            Customer c = new Customer(username);
            c.login();

            database.addLoggedInUser(c);
            return true;
        }

        else {
            return false;
        }
    }

}

