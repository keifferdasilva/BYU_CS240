package Services.results;

/**
 * Generic result class that is implemented by other result classes
 */
public class Result {
    /**
     * Message explaining success or what went wrong in the case of failure
     */
    private String message;

    /**
     * Whether the service was successful or not
     */
    private boolean success;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
