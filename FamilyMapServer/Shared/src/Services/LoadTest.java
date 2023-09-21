package Services;

import Model.Event;
import Model.Person;
import Model.User;
import Services.requests.LoadRequest;
import Services.results.LoadResult;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class LoadTest {

    private LoadRequest request;

    private LoadResult result;

    private Load loadService;

    @BeforeEach
    public void setUp(){
        request = new LoadRequest();
        ArrayList<Event> events = new ArrayList<>();
        events.add(new Event("1234", "keifferd", "Keiffer_D", 12, 14,
                "USA", "Provo", "birth", 1998));
        events.add(new Event("1111", "keifferd", "Keiffer_D", 12, 14,
                "USA", "Provo", "death", 2050));

        ArrayList<Person> people = new ArrayList<>();
        people.add(new Person("Keiffer_D", "keifferd", "Keiffer", "da Silva", "m"));
        people.add(new Person("Robson_D", "keifferd", "Robson", "da Silva", "m"));

        ArrayList<User> users = new ArrayList<>();
        users.add(new User("keifferd", "pass", "email", "Keiffer", "da Silva",
                "m", "1234"));
        users.add(new User("joshua", "pass", "email", "Joshua", "Davis",
                "m", "4321"));

        request.setEvents(events);
        request.setPersons(people);
        request.setUsers(users);

    }

    @Test
    public void LoadSuccess(){
        loadService = new Load();
        result = loadService.load(request);
        assertTrue(result.isSuccess());
        assertEquals("Successfully added 2 users, 2 persons, and 2 events to the database.", result.getMessage());
    }

    @Test
    public void LoadFailure(){
        request.getUsers().get(0).setUsername(null);
        loadService = new Load();
        result = loadService.load(request);
        assertFalse(result.isSuccess());
        assertEquals("Error: Error encountered while inserting a user into the database", result.getMessage());
    }
}
