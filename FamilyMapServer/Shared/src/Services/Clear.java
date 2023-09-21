package Services;
import DataAccess.*;
import Services.results.ClearResult;

/**
 * Service for deleting all the items in the database
 */
public class Clear {
    /**
     * Delete all items from the database
     * @return Result object with a message of success or failure
     */
   public ClearResult clear(){
        Database db = new Database();
        ClearResult result = new ClearResult();
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

            db.closeConnection(true);
            result.setMessage("Clear succeeded.");
            System.out.println("Clear succeeded");
            result.setSuccess(true);
        }
        catch(DataAccessException ex){
            ex.printStackTrace();
            db.closeConnection(false);
            result = new ClearResult("Error: " + ex.getMessage(), false);
        }
        return result;
    }
}
