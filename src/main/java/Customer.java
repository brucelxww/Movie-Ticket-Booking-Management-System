public class Customer {
    private boolean loggedIn = false;
    private String username;

    public Customer(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void login() {
        loggedIn = true;
    }

}
