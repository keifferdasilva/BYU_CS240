package Services;

import DataAccess.AuthTokenDAO;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.PersonDAO;
import Model.AuthToken;
import Model.Person;
import Services.results.PeopleResult;
import Services.results.PersonResult;

import java.util.ArrayList;

/**
 * Return a person or a list of all the people in database
 */
public class PersonService {
    private String authtoken;


    public PersonService(String authtoken){
        this.authtoken = authtoken;
    }

    /**
     * Return a person from the person ID
     * @param personID The ID of the person to be found.
     * @return Result object with a message of success or failure
     */
    public PersonResult getPerson(String personID){
        Database db = new Database();
        PersonResult result = new PersonResult();
        try {
            PersonDAO dao = new PersonDAO(db.getConnection());
            Person person = dao.getPerson(personID);
            AuthTokenDAO authTokenDAO = new AuthTokenDAO(db.getConnection());
            AuthToken authToken = authTokenDAO.getAuthToken(authtoken);

            if(authToken == null){
                throw new DataAccessException("That authtoken does not exist");
            }
            String username = authToken.getUsername();
            if(person == null){
                throw new DataAccessException("A person with that id could not be found");
            }
            if(person.getAssociatedUsername().equals(username)){
                result.setSuccess(true);
                result.setPersonID(person.getPersonID());
                result.setAssociatedUsername(username);
                result.setFirstName(person.getFirstName());
                result.setLastName(person.getLastName());
                result.setGender(person.getGender());
                result.setFatherID(person.getFatherID());
                result.setMotherID(person.getMotherID());
                result.setSpouseID(person.getSpouseID());
            }
            else{
                throw new DataAccessException("You do not have access to this person");
            }
            db.closeConnection(true);
        }
        catch(DataAccessException ex){
            ex.printStackTrace();
            db.closeConnection(false);
            result.setSuccess(false);
            result.setMessage("Error: " + ex.getMessage());
            return result;
        }
        return result;
    }

    /**
     * Return a list of all the people in the database
     * @return An ArrayList of all people
     */
    public PeopleResult getPeople(){
        Database db = new Database();
        PeopleResult result = new PeopleResult();
        try {
            PersonDAO dao = new PersonDAO(db.getConnection());
            AuthTokenDAO authTokenDAO = new AuthTokenDAO(db.getConnection());

            AuthToken authToken = authTokenDAO.getAuthToken(authtoken);

            if(authToken == null){
                throw new DataAccessException("That authtoken does not exist");
            }
            String username = authToken.getUsername();
            ArrayList<Person> people = dao.getPeople(username);

            result.setData(people);
            result.setSuccess(true);
            db.closeConnection(true);
        }
        catch(DataAccessException ex){
            ex.printStackTrace();
            db.closeConnection(false);
            result.setSuccess(false);
            result.setMessage("Error: " + ex.getMessage());
            return result;
        }
        return result;
    }
}
