package Services;

import DataAccess.*;
import Model.AuthToken;
import Model.Event;
import Model.User;
import Services.results.EventResult;
import Services.results.EventsResult;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class EventTest {

    private Event event1;

    private Event event2;

    @BeforeEach
    public void setUp() throws DataAccessException {
        Clear clear = new Clear();
        clear.clear();


        Database db = new Database();
        UserDAO userDAO = new UserDAO(db.getConnection());
        User user = new User("keifferd", "pass", "email", "Keiffer",
                "da Silva", "m", "1234");
        userDAO.addUser(user);
        AuthTokenDAO authTokenDAO = new AuthTokenDAO(db.getConnection());
        authTokenDAO.addAuthToken(new AuthToken("1111", "keifferd"));


        EventDAO dao = new EventDAO(db.getConnection());
        event1 = new Event("1234", "keifferd", "1234", 12, 34, "USA", "Provo", "birth", 1998);
        event2 = new Event("4321", "keifferd", "1234", 34, 12, "USA", "Orem", "death", 2077);
        dao.insert(event1);
        dao.insert(event2);
        db.closeConnection(true);


    }

    private void addSecondUser() throws DataAccessException{
        Database db = new Database();
        UserDAO userDAO = new UserDAO(db.getConnection());
        User user = new User("seconduser", "pass", "email", "Second",
                "User", "m", "2123");
        userDAO.addUser(user);
        AuthTokenDAO authTokenDAO = new AuthTokenDAO(db.getConnection());
        authTokenDAO.addAuthToken(new AuthToken("second", "seconduser"));

        EventDAO dao = new EventDAO(db.getConnection());
        Event secondEvent1 = new Event("2222", "seconduser", "2123", 43, 21, "USA", "Provo", "birth", 1999);
        Event secondEvent2 = new Event("3333", "seconduser", "2123", 21, 43, "USA", "Orem", "death", 2078);
        dao.insert(secondEvent1);
        dao.insert(secondEvent2);
        db.closeConnection(true);
    }

    @Test
    public void findEventSuccess(){
        //Make sure event 1 and 2 are found with the right id
        EventService eventService = new EventService("1111");
        //Make sure event1 found is the same as event1 inserted
        EventResult event1Result = eventService.getEvent(event1.getEventID());
        Event event1Copy = new Event(event1Result.getEventID(), event1Result.getAssociatedUsername(), event1Result.getPersonID(), event1Result.getLatitude(), event1Result.getLongitude(), event1Result.getCountry(), event1Result.getCity(), event1Result.getEventType(), event1Result.getYear());
        assertEquals(event1, event1Copy);
        //Make sure event2 found is the same event2 inserted
        EventResult event2Result = eventService.getEvent(event2.getEventID());
        Event event2Copy = new Event(event2Result.getEventID(), event2Result.getAssociatedUsername(), event2Result.getPersonID(), event2Result.getLatitude(), event2Result.getLongitude(), event2Result.getCountry(), event2Result.getCity(), event2Result.getEventType(), event2Result.getYear());
        assertEquals(event2, event2Copy);
    }

    @Test
    public void findEventFail(){
        //Wrong authtoken
        EventService eventService = new EventService("2222");
        EventResult result = eventService.getEvent(event1.getEventID());
        assertFalse(result.isSuccess());
        assertEquals("Error: That authtoken does not exist", result.getMessage());

        //Wrong event id
        eventService = new EventService("1111");
        result = eventService.getEvent("wrong");
        assertFalse(result.isSuccess());
        assertEquals("Error: An event with that id could not be found", result.getMessage());

        //event does not belong to user
        try {
            addSecondUser();
        }
        catch(DataAccessException ex){
            ex.printStackTrace();
        }
        result = eventService.getEvent("2222");
        assertFalse(result.isSuccess());
        assertEquals("Error: You do not have access to this event", result.getMessage());
        result = eventService.getEvent("3333");
        assertFalse(result.isSuccess());
        assertEquals("Error: You do not have access to this event", result.getMessage());

        eventService = new EventService("second");
        result = eventService.getEvent(event1.getEventID());
        assertFalse(result.isSuccess());
        assertEquals("Error: You do not have access to this event", result.getMessage());
        result = eventService.getEvent(event2.getEventID());
        assertFalse(result.isSuccess());
        assertEquals("Error: You do not have access to this event", result.getMessage());
    }

    @Test
    public void findEventsSuccess(){
        EventService eventService = new EventService("1111");
        EventsResult result = eventService.getAllEvents();
        assertEquals(2, result.getData().size());
        assertEquals(event1, result.getData().get(0));
        assertEquals(event2, result.getData().get(1));

        //add more events with different user and find only ones belonging to users
        try {
            addSecondUser();
        }
        catch(DataAccessException ex){
            ex.printStackTrace();
        }
        result = eventService.getAllEvents();
        assertEquals(2, result.getData().size());
        assertEquals(event1, result.getData().get(0));
        assertEquals(event2, result.getData().get(1));
    }

    @Test
    public void findEventsFail(){
        //wrong authtoken
        EventService eventService = new EventService("2222");
        EventsResult result = eventService.getAllEvents();
        assertNull(result.getData());
        assertFalse(result.isSuccess());
        assertEquals("Error: That authtoken does not exist", result.getMessage());
    }
}
