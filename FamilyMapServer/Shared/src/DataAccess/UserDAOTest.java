package DataAccess;

import Model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;
import java.io.File;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

public class UserDAOTest {

    private UserDAO dao;
    private Database db;

    private User user;

    @BeforeEach
    public void setUp() throws DataAccessException{
        db = new Database();
        Connection conn = db.getConnection();
        dao = new UserDAO(conn);
        dao.clearUserTable();
        user = new User("keifferd", "pass", "keifferd@byu.edu", "Keiffer",
                "da Silva", "m", "1234");

    }

    @AfterEach
    public void tearDown() {
        // Here we close the connection to the database file, so it can be opened again later.
        // We will set commit to false because we do not want to save the changes to the database
        // between test cases.
        db.closeConnection(false);
    }

    @Test
    public void AddUserPass() throws DataAccessException{
        dao.addUser(user);
        assertEquals(user, dao.getUser("keifferd"));
    }

    @Test
    public void AddUserFail() throws DataAccessException{
        dao.addUser(user);
        assertThrows(DataAccessException.class, () -> dao.addUser(user));
    }

    @Test
    public void findUserPass() throws DataAccessException{
        dao.addUser(user);
        User returnedUser = dao.getUser(user.getUsername());
        assertNotNull(returnedUser);
        assertEquals(user, returnedUser);
    }

    @Test
    public void findUserFail() throws DataAccessException{
        //check nothing is returned for empty database
        assertNull(dao.getUser(user.getUsername()));
        //add user then clear the table
        dao.addUser(user);
        dao.clearUserTable();
        //check that even after adding and removing a user that nothing is returned
        assertNull(dao.getUser(user.getUsername()));
    }

    @Test
    public void clearTablePass() throws DataAccessException{
        //Make sure user was successfully added
        dao.addUser(user);
        assertNotNull(dao.getUser(user.getUsername()));
        //Make sure user is deleted by clearUserTable
        dao.clearUserTable();
        assertNull(dao.getUser(user.getUsername()));
    }

    @Test
    public void updateUserTest() throws DataAccessException{
        dao.addUser(user);
        //Make sure the user is not update if you have the wrong username
        dao.updateUser("newID", "doesn't exist");
        assertEquals(user.getPersonID(), dao.getUser(user.getUsername()).getPersonID());
        //Now the user should be updated and have the newID
        dao.updateUser("newID", user.getUsername());
        assertEquals("newID", dao.getUser(user.getUsername()).getPersonID());
    }

    @Test
    public void validateUserTest() throws DataAccessException{
        dao.addUser(user);
        assertTrue(dao.validate(user.getUsername(), user.getPassword()));
        assertFalse(dao.validate(user.getUsername(), "wrong password"));
        assertFalse(dao.validate("wrong username", user.getPassword()));
        assertFalse(dao.validate("wrong username", "wrong password"));
    }
}
