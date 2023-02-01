
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;

import java.io.*;

public class UserRegisterLoginTest {
    private userDatabase database;
    private UserRegisterLogin registerLogin;
    private final PrintStream stdOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void init() {
        database = new userDatabase();
        registerLogin = new UserRegisterLogin(database);
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void end() {
        System.setOut(stdOut);

    }

    @Test
    public void CustomerRegisterLoginTest() {
        assertTrue(registerLogin.register("Tom", "12345"));
        assertTrue(registerLogin.userLogin("Tom", "12345", "customer"));
    }

    @Test
    public void CustomerRegisterInvalidTest() {
        assertFalse(registerLogin.register("Bob", "12345"));

    }

    @Test
    public void CustomerLoginInvalidTest() {
        assertFalse(registerLogin.userLogin("adsfasdgfa", "12345", "customer"));
        assertFalse(registerLogin.userLogin("Bob", "12345asd", "customer"));
    }

    //sprint 2 test cases
    @Test
    public void StaffLoginTest() {
        assertTrue(registerLogin.userLogin("Martin", "12345", "staff"));
    }

    @Test
    public void ManagerLoginTest() {
        assertTrue(registerLogin.userLogin("Tod", "12345", "manager"));
    }


    //since the username check return true if the username is not taken (not in the database), it would return false if a user already exists
    @Test
    public void staffUsernameCheck() {
        assertFalse(registerLogin.UsernameCheck("Martin", "staff"));
    }

    @Test
    public void managerUsernameCheck() {
        assertFalse(registerLogin.UsernameCheck("Tod", "manager"));
    }

    @Test
    public void addStaffTest() {
        database.addStaff("Eric", "12345");
        assertFalse(registerLogin.UsernameCheck("Eric", "staff"));
    }

    @Test
    public void removeStaffTest() {
        database.getLoggedIn();
        database.removeStaff("Martin");
        assertTrue(registerLogin.UsernameCheck("Martin", "staff"));
    }


    @Test
    public void DatabaseUpload() {
        assertTrue(database.updateDatabase());
    }
}
