package framework.tools;

import aquality.selenium.browser.AqualityServices;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;

public class ApiTool {

    private void checkStatusCode (Response response, int expStatusCode){
        response.then().assertThat().statusCode(expStatusCode);
    }

    public Response get(String uri) {
        AqualityServices.getLogger().info("Connecting with " + uri);
        Response response = RestAssured.get(uri).andReturn();
        AqualityServices.getLogger().info("Checking status code");
        checkStatusCode(response, HttpStatus.SC_OK);
        return response;
    }

    public Response get(String uri, int expStatusCode) {
        AqualityServices.getLogger().info("Connecting with " + uri);
        Response response = RestAssured.get(uri).andReturn();
        AqualityServices.getLogger().info("Checking status code");
        checkStatusCode(response, expStatusCode);
        return response;
    }

    public Response post(String uri, String body, String postfix) {
        AqualityServices.getLogger().info("Connecting with " + uri);
        RestAssured.baseURI = uri;
        Response response = RestAssured.given().contentType(ContentType.JSON)
                .body(body).post(postfix);
        AqualityServices.getLogger().info("Checking status code");
        checkStatusCode(response, HttpStatus.SC_CREATED);
        return response;
    }
}
