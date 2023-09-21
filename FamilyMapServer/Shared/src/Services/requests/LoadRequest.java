package Services.requests;

import Model.Event;
import Model.Person;
import Model.User;

import java.util.ArrayList;

/**
 * The request body for Load service
 */
public class LoadRequest {

    /**
     * Array of users to add
     */
    ArrayList<User> users;

    /**
     * Array of persons to add
     */
    ArrayList<Person> persons;

    /**
     * Array of events to add
     */
    ArrayList<Event> events;

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public ArrayList<Person> getPersons() {
        return persons;
    }

    public void setPersons(ArrayList<Person> persons) {
        this.persons = persons;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }
}
