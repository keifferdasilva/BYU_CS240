package Services;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.UserDAO;
import Model.User;
import Services.requests.LoginRequest;
import Services.results.LoginResult;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {
    private LoginRequest request;

    private LoginResult result;

    private Login login;

    @BeforeEach
    public void setUp() throws DataAccessException {
        Clear clear = new Clear();
        clear.clear();
        login = new Login();
        request = new LoginRequest();
        request.setUsername("keifferd");
        request.setPassword("pass");

        Database db = new Database();
        UserDAO userAccess = new UserDAO(db.getConnection());
        User user = new User("keifferd", "pass", "email", "Keiffer",
                "da Silva", "m", "1234");
        userAccess.addUser(user);
        db.closeConnection(true);
    }

    @Test
    public void LoginSuccess(){
        result = login.login(request);
        assertNotNull(result.getAuthtoken());
        assertEquals(result.getUsername(), request.getUsername());
    }

    @Test
    public void LoginPasswordFailure(){
        request.setPassword("past");
        result = login.login(request);
        assertFalse(result.isSuccess());
        assertEquals("Error: Wrong password", result.getMessage());
    }

    @Test
    public void LoginUserNameFailure(){
        request.setUsername("keiffers");
        result = login.login(request);
        assertFalse(result.isSuccess());
        assertEquals("Error: User could not be found", result.getMessage());
    }
}
