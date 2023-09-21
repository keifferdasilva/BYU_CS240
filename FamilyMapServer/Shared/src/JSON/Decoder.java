package JSON;
import com.google.gson.*;

import java.io.Reader;

public class Decoder {
    public static Object deserialize(Reader json, Class<?> classToUse){
        Gson gson = new Gson();
        Object returnVal = gson.fromJson(json, classToUse);
        return returnVal;
    }
}
