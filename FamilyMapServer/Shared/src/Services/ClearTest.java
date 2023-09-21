package Services;

import DataAccess.*;
import Services.requests.RegisterRequest;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class ClearTest {


    @BeforeEach
    public void setUp(){
        Register register = new Register();
        RegisterRequest request = new RegisterRequest();
        request.setUsername("username");
        request.setPassword("password");
        request.setFirstName("First name");
        request.setLastName("Last name");
        request.setGender("m");
        request.setEmail("email");

        register.register(request);


    }

    @Test
    public void ClearEmpty() throws DataAccessException{
        //Make sure clear still works even with empty database
        Clear clear = new Clear();
        clear.clear();
        Database db = new Database();
        UserDAO dao = new UserDAO(db.getConnection());
        assertNull(dao.getUser("username"));
        db.closeConnection(false);
        clear.clear();
        dao = new UserDAO(db.getConnection());
        assertNull(dao.getUser("username"));
        db.closeConnection(false);
    }

    @Test
    public void ClearTest() throws DataAccessException{
        Clear clear = new Clear();
        clear.clear();
        Database db = new Database();
        UserDAO dao = new UserDAO(db.getConnection());
        assertNull(dao.getUser("username"));
        db.closeConnection(false);
    }

}
