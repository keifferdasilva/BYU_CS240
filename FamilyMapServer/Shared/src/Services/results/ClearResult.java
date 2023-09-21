package Services.results;

/**
 * Whether Clear was successful
 */
public class ClearResult extends Result {

    public ClearResult(){
        setMessage(null);
    }
    public ClearResult(String message, boolean success){
        setMessage(message);
        setSuccess(success);
    }
}
