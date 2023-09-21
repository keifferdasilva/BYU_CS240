package Services;

import DataAccess.*;
import Model.*;
import Services.Generation.TreeGenerator;
import Services.requests.RegisterRequest;
import Services.results.RegisterResult;
import java.util.UUID;


/**
 * Create a user
 */
public class Register {
    /**
     * Register the user using the information provided in the RegisterRequest object
     * @param request Request object with username, password, etc.
     * @return Result object with a message of success or failure
     */
    public RegisterResult register(RegisterRequest request){
        Database db = new Database();
        RegisterResult result = new RegisterResult();
        try{
            db.openConnection();

            //Create a new user object for the use to be registered
            UserDAO userAccess = new UserDAO(db.getConnection());
            String personID = UUID.randomUUID().toString();
            User user = new User(request.getUsername(), request.getPassword(), request.getEmail(), request.getFirstName(),
                    request.getLastName(), request.getGender(), personID);
            userAccess.addUser(user);

            //Create an authtoken for the user
            String authToken = UUID.randomUUID().toString();
            AuthToken authtoken = new AuthToken(authToken, request.getUsername());
            AuthTokenDAO authTokenAccess = new AuthTokenDAO(db.getConnection());
            authTokenAccess.addAuthToken(authtoken);

            //Create a new person for the user
            PersonDAO personAccess = new PersonDAO(db.getConnection());
            Person person;
            TreeGenerator treeGenerator = new TreeGenerator(request.getUsername(), db.getConnection());
            person = treeGenerator.generatePerson(request.getGender(), 4, 2007, user.getPersonID());
            person.setFirstName(request.getFirstName());
            person.setLastName(request.getLastName());

            personAccess.updatePerson(person);

            userAccess.updateUser(person.getPersonID(), user.getUsername());

            //treeGenerator.generatePerson(person.getGender(), 4);
            db.closeConnection(true);

            //Create the result object to be returned
            result.setUsername(request.getUsername());
            result.setPersonID(person.getPersonID());
            result.setAuthtoken(authtoken.getAuthToken());
            result.setSuccess(true);
        }
        catch(DataAccessException ex){
            ex.printStackTrace();
            db.closeConnection(false);
            result = new RegisterResult("Error: " + ex.getMessage(), false);
        }
        return result;
    }
}
