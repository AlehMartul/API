package framework.tools;

import aquality.selenium.browser.AqualityServices;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class ApiTool {

    public Response getResponse(String uri) {
        AqualityServices.getLogger().info("Connecting with " + uri);
        Response response = RestAssured.get(uri).andReturn();
        return response;
    }

    public JSONObject getJSONObjectFromArray(String response, int numberOfObject){
        JSONArray jArray = (JSONArray) new JSONTokener(response).nextValue();
        JSONObject jsonObject = jArray.getJSONObject(numberOfObject);
        return jsonObject;
    }

    public Response makePost(String uri, String body, String postfix) {
        AqualityServices.getLogger().info("Connecting with " + uri);
        RestAssured.baseURI = uri;
        Response response = RestAssured.given().contentType(ContentType.JSON)
                .body(body).post(postfix);
        return response;
    }
}
