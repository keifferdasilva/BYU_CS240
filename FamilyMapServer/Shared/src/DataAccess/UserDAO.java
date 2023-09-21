package DataAccess;

import Model.User;

import java.io.File;
import java.sql.*;

/**
 * Class for accessing the user table of the database
 */
public class UserDAO {

    /**
     * The connection to the database
     */
    private final Connection conn;

    public UserDAO(Connection conn){
        this.conn = conn;
    }

    /**
     * Add a user based on the data members of the User object
     * @param user The user object
     */
    public void addUser(User user) throws DataAccessException{

        String sql = "INSERT INTO user (username, password, email, firstName, lastName, gender, personID) VALUES(?,?,?,?,?,?,?)";

        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getFirstName());
            stmt.setString(5, user.getLastName());
            stmt.setString(6, user.getGender());
            stmt.setString(7, user.getPersonID());

            if (stmt.executeUpdate() == 1) {
                System.out.println("Update user " + user.getUsername());
            } else {
                System.out.println("Failed to update user " + user.getUsername());
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
            throw new DataAccessException("Error encountered while inserting a user into the database");
        }
    }

    public void updateUser(String personID, String username) throws DataAccessException{
        String sql = "UPDATE user SET personID = ? WHERE username = ?;";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, personID);
            stmt.setString(2, username);

            if (stmt.executeUpdate() == 1) {
                System.out.println("Updated user " + username);
            } else {
                System.out.println("Failed to update user " + username);
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
            throw new DataAccessException("Error encountered while updating user");
        }
    }

    /**
     * Check whether the username and password are a valid combination
     * @param username The username entered
     * @param password The password entered
     * @return True if the username/password combination exists
     */
    public boolean validate(String username, String password) throws DataAccessException{
        ResultSet rs;
        String sql = "SELECT password FROM user WHERE username = ?; ";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if(rs.next()) {
                String userPassword = rs.getString(1);
                if(userPassword.equals(password)){
                    return true;
                }
                else{
                    return false;
                }
            }
            else{
                return false;
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
            throw new DataAccessException("Error encountered while validating password");
        }
    }

    /**
     * Get a user object from the given username
     * @param username The username of the user
     * @return A user object
     */
    public User getUser(String username) throws DataAccessException{
        User userReturn = null;
        ResultSet rs;
        String sql = "SELECT * FROM user WHERE username = ?; ";
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if (rs.next()) {
                String user = rs.getString(1);
                String password = rs.getString(2);
                String email = rs.getString(3);
                String firstName = rs.getString(4);
                String lastName = rs.getString(5);
                String gender = rs.getString(6);
                String personID = rs.getString(7);
                userReturn = new User(user, password, email, firstName, lastName, gender, personID);
            }

        }
        catch(SQLException ex){
            ex.printStackTrace();
            throw new DataAccessException("Error encountered while finding a user in the database");
        }
        return userReturn;
    }

    public void clearUserTable() throws DataAccessException{
        String sql = "DELETE FROM user";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the user table");
        }
    }


}
