package Services;

import Model.AuthToken;
import Model.Person;
import Model.User;
import Services.results.PeopleResult;
import Services.results.PersonResult;
import org.junit.jupiter.api.*;
import DataAccess.*;

import static org.junit.jupiter.api.Assertions.*;


public class PersonTest {

    private Person person1;

    private Person person2;

    @BeforeEach
    public void setUp() throws DataAccessException{
        Clear clear = new Clear();
        clear.clear();


        Database db = new Database();
        UserDAO userDAO = new UserDAO(db.getConnection());
        User user = new User("keifferd", "pass", "email", "Keiffer",
                "da Silva", "m", "1234");
        userDAO.addUser(user);
        AuthTokenDAO authTokenDAO = new AuthTokenDAO(db.getConnection());
        authTokenDAO.addAuthToken(new AuthToken("1111", "keifferd"));


        PersonDAO dao = new PersonDAO(db.getConnection());
        person1 = new Person("1234", "keifferd", "Keiffer", "da Silva", "m");
        dao.addPerson(person1);
        person2 = new Person("4321", "keifferd", "Robson", "da Silva", "m");
        dao.addPerson(person2);
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

        PersonDAO dao = new PersonDAO(db.getConnection());
        Person secondPerson1 = new Person("2123", "seconduser", "Second", "User", "m");
        dao.addPerson(secondPerson1);
        Person secondPerson2 = new Person("2321", "seconduser", "John", "User", "m");
        dao.addPerson(secondPerson2);
        db.closeConnection(true);
    }

    @Test
    public void findPersonSuccess(){
        //Make sure person 1 and person 2 are found with the right id
        PersonService personService = new PersonService("1111");
        //Make sure person1 found is the same as person1 inserted
        PersonResult person1Result = personService.getPerson(person1.getPersonID());
        Person person1copy = new Person(person1Result.getPersonID(), person1Result.getAssociatedUsername(),
                person1Result.getFirstName(), person1Result.getLastName(), person1Result.getGender());
        assertEquals(person1, person1copy);
        //Make sure person2 found is the same as person2 inserted
        PersonResult person2Result = personService.getPerson(person2.getPersonID());
        Person person2copy = new Person(person2Result.getPersonID(), person2Result.getAssociatedUsername(),
                person2Result.getFirstName(), person2Result.getLastName(), person2Result.getGender());
        assertEquals(person2, person2copy);
    }

    @Test
    public void findPersonFail(){
        //wrong authtoken
        PersonService personService = new PersonService("2222");
        PersonResult result = personService.getPerson(person1.getPersonID());
        assertFalse(result.isSuccess());
        assertEquals("Error: That authtoken does not exist", result.getMessage());

        //wrong person id
        personService = new PersonService("1111");
        result = personService.getPerson("wrong");
        assertFalse(result.isSuccess());
        assertEquals("Error: A person with that id could not be found", result.getMessage());

        //person does not belong to user
        try {
            addSecondUser();
        }
        catch(DataAccessException ex){
            ex.printStackTrace();
        }
        result = personService.getPerson("2123");
        assertFalse(result.isSuccess());
        assertEquals("Error: You do not have access to this person", result.getMessage());
        result = personService.getPerson("2321");
        assertFalse(result.isSuccess());
        assertEquals("Error: You do not have access to this person", result.getMessage());

        personService = new PersonService("second");
        result = personService.getPerson("1234");
        assertFalse(result.isSuccess());
        assertEquals("Error: You do not have access to this person", result.getMessage());
        result = personService.getPerson("4321");
        assertFalse(result.isSuccess());
        assertEquals("Error: You do not have access to this person", result.getMessage());
    }

    @Test
    public void findPeopleSuccess(){
        //make sure we get the right people
        PersonService personService = new PersonService("1111");
        PeopleResult result = personService.getPeople();
        assertEquals(2, result.getData().size());
        assertEquals(person1, result.getData().get(0));
        assertEquals(person2, result.getData().get(1));

        //add more people with different user and find only ones belonging to users
        try {
            addSecondUser();
        }
        catch(DataAccessException ex){
            ex.printStackTrace();
        }
        result = personService.getPeople();
        assertEquals(2, result.getData().size());
        assertEquals(person1, result.getData().get(0));
        assertEquals(person2, result.getData().get(1));
    }

    @Test
    public void findPeopleFail(){
        //wrong authtoken
        PersonService personService = new PersonService("2222");
        PeopleResult result = personService.getPeople();
        assertNull(result.getData());
        assertFalse(result.isSuccess());
        assertEquals("Error: That authtoken does not exist", result.getMessage());
    }
}
