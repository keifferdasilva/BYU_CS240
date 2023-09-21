package Services;

import DataAccess.*;
import Model.Event;
import Model.Person;
import Model.User;
import org.junit.jupiter.api.*;
import Services.results.FillResult;

import java.util.ArrayList;

public class FillTest {

    Database db;
    String username;

    PersonDAO personDAO;

    EventDAO eventDAO;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        Clear clear = new Clear();
        clear.clear();
        UserDAO dao = new UserDAO(db.getConnection());
        username = "keifferd";
        User user = new User(username, "pass", "email","Keiffer",
                "da Silva", "m", "1234");
        dao.addUser(user);
        db.closeConnection(true);
    }

    @AfterEach
    public void tearDown(){

    }

    @Test
    public void FillDefaultSuccess() throws DataAccessException{
        Fill fillService = new Fill();
        fillService.fill(username);
        personDAO = new PersonDAO(db.getConnection());
        eventDAO = new EventDAO(db.getConnection());

        ArrayList<Person> people = personDAO.getPeople(username);
        Assertions.assertNotNull(people);
        Assertions.assertEquals(31, people.size());

        ArrayList<Event> events = eventDAO.getEvents(username);
        Assertions.assertNotNull(events);
        Assertions.assertEquals(92, events.size());
        db.closeConnection(false);
    }

    @Test
    public void FillDefaultTwoSuccess() throws DataAccessException{
        Fill fillService = new Fill();
        fillService.fill(username);



        User secondUser = new User("second", "pass", "email2", "Second", "Person", "f",
                "4321");
        UserDAO dao = new UserDAO(db.getConnection());
        dao.addUser(secondUser);
        db.closeConnection(true);
        personDAO = new PersonDAO(db.getConnection());
        eventDAO = new EventDAO(db.getConnection());
        fillService.fill(secondUser.getUsername());

        ArrayList<Person> people = personDAO.getPeople(username);
        Assertions.assertNotNull(people);
        Assertions.assertEquals(31, people.size());

        ArrayList<Event> events = eventDAO.getEvents(username);
        Assertions.assertNotNull(events);
        Assertions.assertEquals(92, events.size());

        ArrayList<Person> people2 = personDAO.getPeople(secondUser.getUsername());
        Assertions.assertNotNull(people2);
        Assertions.assertEquals(31, people2.size());

        ArrayList<Event> events2 = eventDAO.getEvents(secondUser.getUsername());
        Assertions.assertNotNull(events2);
        Assertions.assertEquals(92, events2.size());
        db.closeConnection(false);
    }

    @Test
    public void FillCustomSuccess() throws DataAccessException{
        Fill fillService = new Fill();
        //Make sure fill has the right amount of people/events for 2 generations
        fillService.fill(2, username);

        personDAO = new PersonDAO(db.getConnection());
        eventDAO = new EventDAO(db.getConnection());

        ArrayList<Person> people = personDAO.getPeople(username);
        Assertions.assertNotNull(people);
        Assertions.assertEquals(7, people.size());

        ArrayList<Event> events = eventDAO.getEvents(username);
        Assertions.assertNotNull(events);
        Assertions.assertEquals(20, events.size());

        db.closeConnection(false);

        //Make sure fill deletes the old people/events and has the right amount for 3 generations
        fillService.fill(3, username);

        personDAO = new PersonDAO(db.getConnection());
        eventDAO = new EventDAO(db.getConnection());
        people = personDAO.getPeople(username);
        Assertions.assertEquals(15, people.size());

        events = eventDAO.getEvents(username);
        Assertions.assertEquals(44, events.size());

        db.closeConnection(false);
    }

    @Test
    public void FillUserFail(){
        Fill fillService = new Fill();
        FillResult response = fillService.fill("wrong");
        Assertions.assertFalse(response.isSuccess());
    }

    @Test
    public void FillGenerationFail(){
        Fill fillService = new Fill();
        FillResult response = fillService.fill(-13, username);
        Assertions.assertFalse(response.isSuccess());
    }
}
