package DataAccess;

import Model.AuthToken;
import Model.Person;

import javax.xml.crypto.Data;
import java.sql.*;

/**
 * A class for accessing the AuthToken table of the database
 */
public class AuthTokenDAO {
    /**
     * Connection to the database
     */
    private final Connection conn;

    /**
     * Basic constructor for the DAO
     * @param conn
     */
    public AuthTokenDAO(Connection conn){
        this.conn = conn;
    }
    /**
     * Add a row to the AuthToken table using the AuthToken object's data members
     * @param authToken
     */
    public void addAuthToken(AuthToken authToken) throws DataAccessException{
        String sql = "INSERT INTO authtoken (authtoken, username) VALUES (?,?)";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, authToken.getAuthToken());
            stmt.setString(2, authToken.getUsername());

            if(stmt.executeUpdate() == 1){
                System.out.println("Updated authtoken: " + authToken.getAuthToken());
            }
            else{
                System.out.println("Failed to update authtoken: " + authToken.getAuthToken());
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
            throw new DataAccessException("Error encountered while trying to insert into authtoken table.");
        }
    }

    /**
     * Get an AuthToken object given the authToken
     * @param authToken The randomized authToken string
     * @return AuthToken object
     */
    public AuthToken getAuthToken(String authToken) throws DataAccessException{
        AuthToken authTokenReturn = null;
        ResultSet rs;
        String sql = "SELECT * from authtoken WHERE authtoken = ?;";
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, authToken);
            rs = stmt.executeQuery();
            if (rs.next()) {
                String token = rs.getString(1);
                String username = rs.getString(2);
                authTokenReturn = new AuthToken(token, username);
            }

        }
        catch(SQLException ex){
            ex.printStackTrace();
            throw new DataAccessException("Error encountered while finding an authtoken in the database");
        }


        return authTokenReturn;
    }


    public void clearAuthTokenTable() throws DataAccessException{
        String sql = "DELETE FROM authtoken";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the authtoken table");
        }
    }
}
