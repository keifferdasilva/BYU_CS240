package DataAccess;

import Model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class PersonDAOTest {

    private PersonDAO dao;
    private Database db;

    private Person person;

    @BeforeEach
    public void setUp() throws DataAccessException{
        db = new Database();
        Connection conn = db.getConnection();
        dao = new PersonDAO(conn);
        dao.clearPersonTable();
        person = new Person("1234", "keifferd", "Keiffer", "da Silva",
                "male", "4321", "2341", "3214");
    }

    @AfterEach
    public void tearDown() {
        // Here we close the connection to the database file, so it can be opened again later.
        // We will set commit to false because we do not want to save the changes to the database
        // between test cases.
        db.closeConnection(false);
    }

    @Test
    public void addPersonPass() throws DataAccessException{
        dao.addPerson(person);
        Assertions.assertEquals(person, dao.getPerson(person.getPersonID()));
        
    }

    @Test
    public void addPersonFail() throws DataAccessException{
        dao.addPerson(person);
        //Make sure person was added
        Assertions.assertEquals(person, dao.getPerson(person.getPersonID()));
        //Make sure we get an error when adding a duplicate person
        Assertions.assertThrows(DataAccessException.class, () -> dao.addPerson(person));
    }

    @Test
    public void findPersonPass() throws DataAccessException{
        dao.addPerson(person);
        Person returnedPerson = dao.getPerson(person.getPersonID());
        assertNotNull(returnedPerson);
        assertEquals(person, returnedPerson);
    }

    @Test
    public void findPersonFail() throws DataAccessException{
        //check nothing is returned for empty database
        assertNull(dao.getPerson(person.getPersonID()));
        //add person then clear the table
        dao.addPerson(person);
        dao.clearPersonTable();
        //check nothing is returned even after add and clearing table
        assertNull(dao.getPerson(person.getPersonID()));
    }

    @Test
    public void clearPersonPass() throws DataAccessException{
        //Make sure user was successfully added
        dao.addPerson(person);
        assertNotNull(dao.getPerson(person.getPersonID()));
        //Make sure user is deleted by clearUserTable
        dao.clearPersonTable();
        assertNull(dao.getPerson(person.getPersonID()));
    }

    @Test
    public void findPeoplePass() throws DataAccessException{
        dao.addPerson(person);
        Person secondPerson = new Person("4321", "keifferd", "Keiffer", "da Silva",
                "male", "4321", "2341", "3214");
        dao.addPerson(secondPerson);
        ArrayList<Person> people = dao.getPeople("keifferd");
        assertEquals(2, people.size());
        people.forEach(person1 -> {System.out.println("Person id: " + person1.getPersonID());});
    }

    @Test
    public void updatePeopleTest() throws DataAccessException{
        dao.addPerson(person);
        Person secondPerson = new Person(person.getPersonID(), "keifferd", "Anna", "Smith",
                "female", "1111", "2222", "3333");
        dao.updatePerson(secondPerson);
        assertEquals(secondPerson, dao.getPerson(secondPerson.getPersonID()));
        //Make sure update doesn't update the person with a different personID
        Person thirdPerson = new Person("4321", "keifferd", "Keiffer", "da Silva",
                "male", "1111", "2222", "3333");
        dao.updatePerson(thirdPerson);
        assertEquals(secondPerson, dao.getPerson(secondPerson.getPersonID()));
    }
}
