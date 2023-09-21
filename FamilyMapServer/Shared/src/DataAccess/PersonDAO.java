package DataAccess;

import Model.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Class for accessing the person table of the database
 */
public class PersonDAO {

    private final Connection conn;
    public PersonDAO(Connection conn){
        this.conn = conn;
    }

    /**
     * Add a person row based on the data members of the Person object given
     * @param person A person object
     */
    public void addPerson(Person person) throws DataAccessException{
        String sql = "INSERT INTO person (personID, associatedUsername, firstName, lastName, gender, fatherID, motherID, spouseID) VALUES(?,?,?,?,?,?,?,?)";
        
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, person.getPersonID());
            stmt.setString(2, person.getAssociatedUsername());
            stmt.setString(3, person.getFirstName());
            stmt.setString(4, person.getLastName());
            stmt.setString(5, person.getGender());
            stmt.setString(6, person.getFatherID());
            stmt.setString(7, person.getMotherID());
            stmt.setString(8, person.getSpouseID());

            if (stmt.executeUpdate() == 1) {
                System.out.println("Added person " + person.getPersonID());
            } else {
                System.out.println("Failed to update person " + person.getPersonID());
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
            throw new DataAccessException("Error encountered while inserting a person into the database");
        }
    }


    /**
     * Update a person with new data members
     * @param person
     * @throws DataAccessException
     */
    public void updatePerson(Person person) throws DataAccessException{
        String sql = "UPDATE person SET associatedUsername = ?, firstName = ?, lastName = ?, gender = ?, fatherID = ?, motherID = ?, spouseID = ? WHERE personID = ?";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, person.getAssociatedUsername());
            stmt.setString(2, person.getFirstName());
            stmt.setString(3, person.getLastName());
            stmt.setString(4, person.getGender());
            stmt.setString(5, person.getFatherID());
            stmt.setString(6, person.getMotherID());
            stmt.setString(7, person.getSpouseID());
            stmt.setString(8, person.getPersonID());

            if (stmt.executeUpdate() == 1) {
                System.out.println("Updated person " + person.getPersonID());
            } else {
                System.out.println("Failed to update person " + person.getPersonID());
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
            throw new DataAccessException("Error encountered while updating person");
        }
    }

    /**
     * Get a person object from the given person ID
     * @param personID The ID of the person
     * @return A person object
     */
    public Person getPerson(String personID) throws DataAccessException{
        Person personReturn = null;
        ResultSet rs;
        String sql = "SELECT * FROM person WHERE personID = ?; ";
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                String personReturnID = rs.getString(1);
                String associatedUsername = rs.getString(2);
                String firstName = rs.getString(3);
                String lastName = rs.getString(4);
                String gender = rs.getString(5);
                String fatherID = rs.getString(6);
                String motherID = rs.getString(7);
                String spouseID = rs.getString(8);
                personReturn = new Person(personReturnID, associatedUsername, firstName, lastName, gender, fatherID, motherID, spouseID);
            }

        }
        catch(SQLException ex){
            ex.printStackTrace();
            throw new DataAccessException("Error encountered while finding a user in the database");
        }
        return personReturn;
    }

    /**
     * Get a list of all person objects in the database
     * @return An ArrayList of Person objects
     */
    public ArrayList<Person> getPeople(String username) throws DataAccessException{
        ArrayList<Person> people = new ArrayList<>();
        ResultSet rs;
        String sql = "SELECT * FROM person WHERE associatedUsername = ?; ";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            while(rs.next()){
                String personReturnID = rs.getString(1);
                String associatedUsername = rs.getString(2);
                String firstName = rs.getString(3);
                String lastName = rs.getString(4);
                String gender = rs.getString(5);
                String fatherID = rs.getString(6);
                String motherID = rs.getString(7);
                String spouseID = rs.getString(8);
                Person person = new Person(personReturnID, associatedUsername, firstName, lastName, gender, fatherID, motherID, spouseID);
                people.add(person);
            }
        } catch(SQLException e){
            e.printStackTrace();
            throw new DataAccessException("Error encountered while trying to find people");
        }

        return people;
    }

    public void clearPersonTable() throws DataAccessException{
        String sql = "DELETE FROM person";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the person table");
        }
    }

    public void clearPersonTable(String username) throws DataAccessException{
        String sql = "DELETE FROM person WHERE associatedUsername = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the person table");
        }
    }
}
