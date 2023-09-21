package Services.requests;

/**
 * The request body for login
 */
public class LoginRequest {

    /**
     * The user trying to login
     */
    private String username;

    /**
     * The password of the user
     */
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
