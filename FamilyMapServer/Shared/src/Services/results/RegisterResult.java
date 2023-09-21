package Services.results;

/**
 * The result of the register action
 */
public class RegisterResult extends Result{
    /**
     * The authToken for the user created
     */
    private String authtoken;
    /**
     * The username for the user created
     */
    private String username;
    /**
     * The ID of the person associated with the user created
     */
    private String personID;

    public String getAuthtoken() {
        return authtoken;
    }

    public RegisterResult(){
        this.authtoken = null;
        this.personID = null;
        this.username = null;
    }

    public RegisterResult(String message, boolean success){
        this.authtoken = null;
        this.personID = null;
        this.username = null;
        setMessage(message);
        setSuccess(success);
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
