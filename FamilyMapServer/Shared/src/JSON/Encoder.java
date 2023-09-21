package JSON;

import com.google.gson.Gson;

public class Encoder {
    public static String serialize(Object object){
        Gson gson = new Gson();
        String json = gson.toJson(object);
        return json;
    }
}
