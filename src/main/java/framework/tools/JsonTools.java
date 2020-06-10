package framework.tools;

import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class JsonTools {

    public JSONObject getJSONObjectFromArray(String response, int numberOfObject){
        JSONArray jArray = (JSONArray) new JSONTokener(response).nextValue();
        JSONObject jsonObject = jArray.getJSONObject(numberOfObject);
        return jsonObject;
    }

    public static JSONArray getJSONArrayFromString(String response){
        JSONArray jArray = (JSONArray) new JSONTokener(response).nextValue();
        return jArray;
    }
}
