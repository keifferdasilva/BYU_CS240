package Model;

import java.util.Objects;

/**
 * The username and it's associated authtoken.
 */
public class AuthToken {
    /**
     * The randomized token used for authorization.
     */
    private String authToken;
    /**
     * The username associated with the authToken.
     */
    private String username;

    /**
     * Create the username with its associated authtoken.
     * @param authToken The randomized token.
     * @param username The username
     */
    public AuthToken(String authToken, String username) {
        this.authToken = authToken;
        this.username = username;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthToken authtoken = (AuthToken) o;
        return Objects.equals(authToken, authtoken.authToken) && Objects.equals(username, authtoken.username);
    }
}
