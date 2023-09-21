package Services;

import DataAccess.AuthTokenDAO;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.EventDAO;
import Model.AuthToken;
import Model.Event;
import Services.results.EventResult;
import Services.results.EventsResult;

import java.util.ArrayList;

/**
 * Class for getting one or all events in the database
 */
public class EventService {

    private String authtoken;

    public EventService(String authtoken){
        this.authtoken = authtoken;
    }

    /**
     * Get one event given its event ID
     * @param eventID
     * @return Result object with a message of success or failure
     */
     public EventResult getEvent(String eventID){
         Database db = new Database();
         EventResult result = new EventResult();

         try{
             EventDAO dao = new EventDAO(db.getConnection());
             Event event = dao.find(eventID);
             AuthTokenDAO authTokenDAO = new AuthTokenDAO(db.getConnection());
             AuthToken authToken = authTokenDAO.getAuthToken(authtoken);
             if(authToken == null){
                 throw new DataAccessException("That authtoken does not exist");
             }
             String username = authToken.getUsername();
             if(event == null){
                 throw new DataAccessException("An event with that id could not be found");
             }

             if(event.getAssociatedUsername().equals(username)){
                 result.setSuccess(true);
                 result.setEventID(event.getEventID());
                 result.setAssociatedUsername(username);
                 result.setPersonID(event.getPersonID());
                 result.setEventType(event.getEventType());
                 result.setCity(event.getCity());
                 result.setCountry(event.getCountry());
                 result.setLatitude(event.getLatitude());
                 result.setLongitude(event.getLongitude());
                 result.setYear(event.getYear());
             }
             else{
                 throw new DataAccessException("You do not have access to this event");
             }
             db.closeConnection(true);

         }
         catch(DataAccessException ex){
             ex.printStackTrace();
             db.closeConnection(false);
             result.setSuccess(false);
             result.setMessage("Error: " + ex.getMessage());
             result.setYear(null);
             result.setLongitude(null);
             result.setLatitude(null);

             return result;
         }
         return result;
     }

    /**
     * Get all the events in the database
     * @return An ArrayList of Result objects
     */
     public EventsResult getAllEvents(){
         Database db = new Database();
         EventsResult result = new EventsResult();
         try{
             EventDAO eventDAO = new EventDAO(db.getConnection());
             AuthTokenDAO authTokenDAO = new AuthTokenDAO(db.getConnection());

             AuthToken authToken = authTokenDAO.getAuthToken(authtoken);

             if(authToken == null){
                 throw new DataAccessException("That authtoken does not exist");
             }
             String username = authToken.getUsername();
             ArrayList<Event> events = eventDAO.getEvents(username);
             result.setData(events);
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
