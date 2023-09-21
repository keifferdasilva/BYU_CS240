package Services;

import DataAccess.*;
import Model.Event;
import Model.Person;
import Model.User;
import Services.requests.LoadRequest;
import Services.results.LoadResult;

import java.util.ArrayList;

/**
 * Clear all objects from the database then add the ones given by the request body
 */
public class Load {

    /**
     * Clear all objects, then add the ones found in request
     * @param request json arrays of user, person, and event objects
     * @return Result object with a message of success or failure
     */
    public LoadResult load(LoadRequest request){
        Database db = new Database();
        LoadResult result = new LoadResult();
        try{
            db.openConnection();
            UserDAO userAccess = new UserDAO(db.getConnection());
            PersonDAO personAccess = new PersonDAO(db.getConnection());
            EventDAO eventAccess = new EventDAO(db.getConnection());
            AuthTokenDAO authTokenAccess = new AuthTokenDAO(db.getConnection());

            userAccess.clearUserTable();
            personAccess.clearPersonTable();
            eventAccess.clear();
            authTokenAccess.clearAuthTokenTable();

            ArrayList<User> usersToAdd = request.getUsers();
            ArrayList<Person> peopleToAdd = request.getPersons();
            ArrayList<Event> eventsToAdd = request.getEvents();

            for(User user : usersToAdd){
                userAccess.addUser(user);
            }
            for(Person person : peopleToAdd){
                personAccess.addPerson(person);
            }
            for(Event event : eventsToAdd){
                eventAccess.insert(event);
            }

            result.setMessage("Successfully added " + usersToAdd.size() + " users, " + peopleToAdd.size() +
                    " persons, and " + eventsToAdd.size() + " events to the database.");
            result.setSuccess(true);

            db.closeConnection(true);
        }
        catch(DataAccessException ex){
            result.setMessage("Error: " + ex.getMessage());
            result.setSuccess(false);
            db.closeConnection(false);
            return result;
        }
        return result;
    }
}
