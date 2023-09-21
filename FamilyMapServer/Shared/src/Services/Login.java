package Services;

import DataAccess.*;
import Model.AuthToken;
import Services.requests.LoginRequest;
import Services.results.LoginResult;
import Model.User;

import java.util.UUID;

/**
 * Logs the user in
 */
public class Login {
    /**
     * Logs the user in and respond with the authToken
     * @param request username and password
     * @return Result object with a message of success or failure
     */
     public LoginResult login(LoginRequest request){
         Database db = new Database();
         LoginResult result = new LoginResult();

         try{
             db.openConnection();

             UserDAO userAccess = new UserDAO(db.getConnection());
             AuthTokenDAO authTokenDAO = new AuthTokenDAO(db.getConnection());
             String userToFind = request.getUsername();
             String passwordToFind = request.getPassword();
             User returnUser = userAccess.getUser(userToFind);
             if(returnUser == null){
                 throw new DataAccessException("User could not be found");
             }
             String authtoken;
             if(userAccess.validate(userToFind, passwordToFind)){
                 authtoken = UUID.randomUUID().toString();
                 AuthToken authToken = new AuthToken(authtoken, userToFind);
                 authTokenDAO.addAuthToken(authToken);
                 result.setUsername(userToFind);
                 result.setSuccess(true);
                 result.setAuthtoken(authtoken);
                 result.setPersonID(returnUser.getPersonID());
             }
             else{
                 throw new DataAccessException("Wrong password");
             }

             db.closeConnection(true);

         }
         catch(DataAccessException ex){
             ex.printStackTrace();
             db.closeConnection(false);
             result = new LoginResult("Error: " + ex.getMessage(), false);
             return result;
         }
         return result;
     }
}
