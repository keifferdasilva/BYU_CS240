package Services.results;

/**
 * The result of the login
 */
public class LoginResult extends Result{
    /**
     * The authToken for the user that logged in
     */
    private String authtoken;

    /**
     * The user who logged in
     */
    private String username;

    /**
     * The person ID associated with the user
     */
    private String personID;

    public LoginResult(){}

    public LoginResult(String message, boolean success){
        setMessage(message);
        setSuccess(success);
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }
}
