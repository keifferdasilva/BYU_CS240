package DataAccess;

import Model.AuthToken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;
import java.io.File;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

public class AuthTokenDAOTest {

    private AuthTokenDAO dao;
    private Database db;

    private AuthToken authtoken;

    @BeforeEach
    public void setUp() throws DataAccessException{
        db = new Database();
        Connection conn = db.getConnection();
        dao = new AuthTokenDAO(conn);
        dao.clearAuthTokenTable();
        authtoken = new AuthToken("1234","keifferd");
    }

    @AfterEach
    public void tearDown() {
        // Here we close the connection to the database file, so it can be opened again later.
        // We will set commit to false because we do not want to save the changes to the database
        // between test cases.
        db.closeConnection(false);
    }

    @Test
    public void addTokenPass() throws DataAccessException{
        dao.addAuthToken(authtoken);
        Assertions.assertEquals(authtoken, dao.getAuthToken(authtoken.getAuthToken()));

    }

    @Test
    public void addTokenFail() throws DataAccessException{
        dao.addAuthToken(authtoken);
        //Make sure person was added
        Assertions.assertEquals(authtoken, dao.getAuthToken(authtoken.getAuthToken()));
        //Make sure we get an error when adding a duplicate person
        Assertions.assertThrows(DataAccessException.class, () -> dao.addAuthToken(authtoken));
    }

    @Test
    public void findTokenPass() throws DataAccessException{
        dao.addAuthToken(authtoken);
        AuthToken returnedAuthToken = dao.getAuthToken(authtoken.getAuthToken());
        assertNotNull(returnedAuthToken);
        assertEquals(authtoken, returnedAuthToken);
    }

    @Test
    public void findTokenFail() throws DataAccessException{
        //check nothing is returned for empty database
        assertNull(dao.getAuthToken(authtoken.getAuthToken()));
        //add person then clear the table
        dao.addAuthToken(authtoken);
        dao.clearAuthTokenTable();
        //check nothing is returned even after add and clearing table
        assertNull(dao.getAuthToken(authtoken.getAuthToken()));
    }

    @Test
    public void clearTokenPass() throws DataAccessException{
        //Make sure user was successfully added
        dao.addAuthToken(authtoken);
        assertNotNull(dao.getAuthToken(authtoken.getAuthToken()));
        //Make sure user is deleted by clearUserTable
        dao.clearAuthTokenTable();
        assertNull(dao.getAuthToken(authtoken.getAuthToken()));
    }
}
