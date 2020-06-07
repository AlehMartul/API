package framework.tools;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class JsonTools {

    public JSONObject getJSONObjectFromArray(String response, int numberOfObject){
        JSONArray jArray = (JSONArray) new JSONTokener(response).nextValue();
        JSONObject jsonObject = jArray.getJSONObject(numberOfObject);
        return jsonObject;
    }
}
