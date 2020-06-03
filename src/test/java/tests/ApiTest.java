package tests;

import aquality.selenium.browser.AqualityServices;
import data.Postfix;
import framework.tools.ApiTool;
import framework.tools.ReadPropertyTool;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import models.User;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ApiTest {

    private static final String RESOURCES_PATH = "src/main/resources/";
    private static final String PROPERTIES_FILE_NAME = "api.properties";
    private static final String URI = new ReadPropertyTool(RESOURCES_PATH, PROPERTIES_FILE_NAME).getProperty("mainUrl");
    private static final String POST_BODY = "{\"title\": \"123\", \"body\": \"45\", \"userId\": 1}";
    private static final int STATUS_CODE_200 = 200;
    private static final int STATUS_CODE_201 = 201;
    private static final int STATUS_CODE_404 = 404;
    private static final String USER_ID = "userId";
    private static final String VALUE_OF_USER_ID = "10";
    private static final int NUMBER_OF_USER_ID_5 = 4;
    private static final String TITLE = "title";
    private static final String BODY = "body";

    @Test()
    public void testGetAllPosts() throws JSONException {
        ApiTool apiTool = new ApiTool();
        Response response = apiTool.getResponse(URI + Postfix.POSTS);
        String allPosts = response.getBody().asString();
        AqualityServices.getLogger().info("Getting all posts " + allPosts);
        AqualityServices.getLogger().info("Checking status code");
        response.then().assertThat().statusCode(STATUS_CODE_200);
    }

    @Test()
    public void testGetPostN99() throws JSONException {
        ApiTool apiTool = new ApiTool();
        Response response = apiTool.getResponse(URI + Postfix.POSTS99);
        String json = response.getBody().asString();
        AqualityServices.getLogger().info("Getting posts N99 " + json);
        JsonPath jsonPath = new JsonPath(json);
        AqualityServices.getLogger().info("Checking " + USER_ID);
        Assert.assertEquals(VALUE_OF_USER_ID, jsonPath.getString(USER_ID), "UserId is wrong");
        AqualityServices.getLogger().info("Checking " + TITLE);
        Assert.assertNotNull(jsonPath.getString(TITLE), "title is empty");
        AqualityServices.getLogger().info("Checking " + BODY);
        Assert.assertNotNull(jsonPath.getString(BODY), "body is empty");
        AqualityServices.getLogger().info("Checking status code");
        response.then().assertThat().statusCode(STATUS_CODE_200);
    }

    @Test()
    public void testGetPost150() throws JSONException {
        ApiTool apiTool = new ApiTool();
        Response response = apiTool.getResponse(URI + Postfix.POSTS150);
        String json = response.getBody().asString();
        AqualityServices.getLogger().info("Getting posts N150 " + json);
        Assert.assertEquals("{}", json, "Body of post is not Null");
        AqualityServices.getLogger().info("Checking status code");
        response.then().assertThat().statusCode(STATUS_CODE_404);
    }

    @Test()
    public void testMakePost() {
        ApiTool apiTool = new ApiTool();
        AqualityServices.getLogger().info("Body of the post: " + POST_BODY);
        AqualityServices.getLogger().info("Posfix is: " + Postfix.POSTS);
        Response response = apiTool.makePost(URI, POST_BODY, String.valueOf(Postfix.POSTS));
        AqualityServices.getLogger().info("Checking status code");
        response.then().assertThat().statusCode(STATUS_CODE_201);
    }

    @Parameters({"name", "username", "email", "address", "phone", "website", "company", "nameValue", "usernameValue",
            "emailValue", "addressValue", "phoneValue", "websiteValue", "companyValue"})
    @Test()
    public void testGetAllUsers(String name, String username, String email, String address, String phone, String website,
                          String company, String nameValue, String usernameValue, String emailValue, String addressValue,
                          String phoneValue, String websiteValue, String companyValue) throws JSONException, NumberFormatException {
        ApiTool apiTool = new ApiTool();
        Response response = apiTool.getResponse(URI + Postfix.USERS);
        AqualityServices.getLogger().info("Getting users info");
        String json = response.getBody().asString();
        JSONObject jsonObject = apiTool.getJSONObjectFromArray(json, NUMBER_OF_USER_ID_5);
        User user = new User();
        user.setAllFields
                (user, nameValue, usernameValue, emailValue, addressValue, phoneValue, websiteValue, companyValue);
        User user1 = new User();
        user1.setAllFields(user1, jsonObject.getString(name), jsonObject.getString(username), jsonObject.getString(email),
                String.valueOf(jsonObject.get(address)), jsonObject.getString(phone), jsonObject.getString(website),
                String.valueOf(jsonObject.get(company)));
        AqualityServices.getLogger().info("Comparing data of user");
        Assert.assertTrue(user.equals(user1), "User data is wrong");
    }

    @Parameters({"name", "username", "email", "address", "phone", "website", "company", "nameValue", "usernameValue",
            "emailValue", "addressValue", "phoneValue", "websiteValue", "companyValue"})
    @Test()
    public void testUser5(String name, String username, String email, String address, String phone, String website,
                          String company, String nameValue, String usernameValue, String emailValue, String addressValue,
                          String phoneValue, String websiteValue, String companyValue)
            throws JSONException, NumberFormatException {
        ApiTool apiTool = new ApiTool();
        Response response = apiTool.getResponse(URI + Postfix.USERS5);
        String json = response.getBody().asString();
        AqualityServices.getLogger().info("Getting users info " + json);
        JsonPath jsonPath = new JsonPath(json);
        User user = new User();
        user.setAllFields
                (user, nameValue, usernameValue, emailValue, addressValue, phoneValue, websiteValue, companyValue);
        User user1 = new User();
        user1.setAllFields(user1, jsonPath.getString(name), jsonPath.getString(username), jsonPath.getString(email),
                jsonPath.getString(address), jsonPath.getString(phone), jsonPath.getString(website),
                jsonPath.getString(company));
        AqualityServices.getLogger().info("Comparing data of user");
        Assert.assertTrue(user.equals(user1), "User data is wrong");
    }
}