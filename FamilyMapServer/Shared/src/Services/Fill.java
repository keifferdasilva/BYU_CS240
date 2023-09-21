package Services;

import DataAccess.*;
import Model.Person;
import Model.User;
import Services.Generation.TreeGenerator;
import Services.results.FillResult;

/**
 * Fill the user's family tree with ancestors
 */
public class Fill {
    /**
     * Fill the user's tree up to the specified number of generations
     * @param numGenerations The number of generations to create
     * @return Result object with a message of success or failure
     */
    public FillResult fill(int numGenerations, String username){
        Database db = new Database();
        FillResult response = new FillResult();



        try{

            db.openConnection();
            if(numGenerations < 0){
                throw new DataAccessException("Number of generations must be greater than 0");
            }
            PersonDAO personAccess = new PersonDAO(db.getConnection());
            EventDAO eventAccess = new EventDAO(db.getConnection());
            UserDAO userAccess = new UserDAO(db.getConnection());
            User user = userAccess.getUser(username);
            if(user == null){
                throw new DataAccessException("The user could not be found");
            }
            personAccess.clearPersonTable(username);
            eventAccess.clear(username);

            TreeGenerator treeGenerator = new TreeGenerator(username, db.getConnection());
            Person person = treeGenerator.generatePerson(user.getGender(), numGenerations, 2007, user.getPersonID());
            person.setFirstName(user.getFirstName());
            person.setLastName(user.getLastName());
            personAccess.updatePerson(person);



            db.closeConnection(true);

            response.setSuccess(true);
            response.setMessage("Successfully added " + treeGenerator.getPeopleAdded()
                    + " persons and " + treeGenerator.getEventsAdded() + " events to the database.");
        }
        catch(DataAccessException ex){
            ex.printStackTrace();
            db.closeConnection(false);
            response = new FillResult();
            response.setMessage("Error: " + ex.getMessage());
            response.setSuccess(false);
            return response;
        }
        return response;
    }

    /**
     * Fill the user's tree up to 4 generations
     * @return Result object with a message of success or failure
     */
    public FillResult fill(String username){
        Database db = new Database();
        FillResult response = new FillResult();

        try{
            db.openConnection();

            PersonDAO personAccess = new PersonDAO(db.getConnection());
            EventDAO eventAccess = new EventDAO(db.getConnection());
            UserDAO userAccess = new UserDAO(db.getConnection());
            User user = userAccess.getUser(username);
            if(user == null){
                throw new DataAccessException("The user could not be found");
            }
            personAccess.clearPersonTable(username);
            eventAccess.clear(username);

            TreeGenerator treeGenerator = new TreeGenerator(username, db.getConnection());
            Person person = treeGenerator.generatePerson(user.getGender(), 4, 2007, user.getPersonID());
            person.setFirstName(user.getFirstName());
            person.setLastName(user.getLastName());
            personAccess.updatePerson(person);



            db.closeConnection(true);

            response.setSuccess(true);
            response.setMessage("Successfully added " + treeGenerator.getPeopleAdded()
                    + " persons and " + treeGenerator.getEventsAdded() + " events to the database.");
        }
        catch(DataAccessException ex){
            ex.printStackTrace();
            db.closeConnection(false);
            response = new FillResult();
            response.setMessage("Error: " + ex.getMessage());
            response.setSuccess(false);
            return response;
        }
        return response;
    }
}
