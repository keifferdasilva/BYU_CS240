package Services;

import Services.requests.RegisterRequest;
import Services.results.RegisterResult;
import org.junit.jupiter.api.*;



public class RegisterTest {

    private RegisterRequest request;

    private RegisterResult result;

    private Register registerService;

    @BeforeEach
    public void setUp(){
        Clear clear = new Clear();
        clear.clear();
        request = new RegisterRequest();
        request.setUsername("user");
        request.setPassword("password");
        request.setEmail("email");
        request.setGender("m");
        request.setFirstName("First");
        request.setLastName("Last");
        registerService = new Register();
    }

    @Test
    public void registerSuccess(){
        result = registerService.register(request);
        Assertions.assertEquals(result.getUsername(), request.getUsername());
        Assertions.assertNotNull(result.getAuthtoken());
        Assertions.assertNotNull(result.getPersonID());
    }

    @Test
    public void registerFailure(){
        //Make sure you can't register with the same user twice.
        result = registerService.register(request);
        RegisterResult secondResult = registerService.register(request);
        Assertions.assertFalse(secondResult.isSuccess());
    }
}
