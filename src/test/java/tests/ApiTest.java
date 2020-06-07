package tests;

import Steps.CheckResponseDataSteps;
import aquality.selenium.browser.AqualityServices;
import data.Postfix;
import framework.tools.ApiTool;
import framework.tools.JsonTools;
import framework.tools.ReadPropertyTool;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import models.Post;
import models.User;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ApiTest {

    private User expectedUser = new User();
    private User expectedUserForJsonObjects = new User();
    private User actualUser = new User();
    private Post actualPost = new Post();
    private Post expectedPost = new Post();
    private Post post = new Post();
    private Post post_plus_1 = new Post();
    private static final String RESOURCES_PATH = "src/main/resources/";
    private static final String PROPERTIES_FILE_NAME = "api.properties";
    private static final int POST_99 = 99;
    private static final int POST_150 = 150;
    private static final int NUMBER_OF_USER_ID_5 = 5;
    private static final String URI = new ReadPropertyTool(RESOURCES_PATH, PROPERTIES_FILE_NAME).getProperty("mainUrl");
    private static final String TITLE_VALUE = "123";
    private static final String BODY_VALUE = "45";
    private static final String USER_ID_VALUE_FOR_POST = "1";
    private static final String POST_BODY = "{\"title\": \"" + TITLE_VALUE + "\", \"body\": \"" + BODY_VALUE + "\"," +
            " \"userId\": " + USER_ID_VALUE_FOR_POST + "}";
    private static final int STATUS_CODE_404 = HttpStatus.SC_NOT_FOUND;
    private static final String USER_ID = "userId";
    private static final String VALUE_OF_USER_ID = "10";
    private static final String TITLE = "title";
    private static final String BODY = "body";
    private static final String ID = "id";

    @Parameters({"nameValue", "usernameValue", "emailValue", "addressValue", "phoneValue", "websiteValue",
            "companyValue", "addressValueForJsonObject", "companyValueForJsonObject"})
    @BeforeGroups("CheckUsers")
    public void before_it(String nameValue, String usernameValue, String emailValue,
                          String addressValueForJsonObjects, String phoneValue, String websiteValue,
                          String companyValueForJsonObjects, String addressValue, String companyValue) {
        CheckResponseDataSteps checkResponseDataSteps = new CheckResponseDataSteps();
        checkResponseDataSteps.setUserObject(expectedUserForJsonObjects, nameValue,
                usernameValue, emailValue, addressValueForJsonObjects, phoneValue, websiteValue,
                companyValueForJsonObjects);
        checkResponseDataSteps.setUserObject(expectedUser, nameValue, usernameValue, emailValue,
                addressValue, phoneValue, websiteValue, companyValue);
    }

    @Test
    public void testGetAllPosts() throws JSONException, NumberFormatException {
        ApiTool apiTool = new ApiTool();
        Response response = apiTool.getResponse(URI + Postfix.POSTS);
        String allPosts = response.getBody().asString();
        AqualityServices.getLogger().info("Getting all posts " + allPosts);
        String json = response.getBody().asString();
        CheckResponseDataSteps checkResponseDataSteps = new CheckResponseDataSteps();
        Assert.assertTrue(checkResponseDataSteps.isArraySortedById(POST_99, json, post, post_plus_1, ID, TITLE, BODY));
    }

    @Test
    public void testGetPostN99() throws JSONException {
        ApiTool apiTool = new ApiTool();
        Response response = apiTool.getResponse(URI + Postfix.POSTS_BY_ID.format(POST_99));
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
    }

    @Test
    public void testGetPost150() throws JSONException {
        ApiTool apiTool = new ApiTool();
        Response response = apiTool.getResponse(URI + Postfix.POSTS_BY_ID.format(POST_150), STATUS_CODE_404);
        String json = response.getBody().asString();
        AqualityServices.getLogger().info("Getting posts N150 " + json);
        Assert.assertEquals("{}", json, "Body of post is not Null");
        AqualityServices.getLogger().info("Checking status code");
    }

    @Test
    public void testMakePost() throws JSONException, NumberFormatException {
        ApiTool apiTool = new ApiTool();
        AqualityServices.getLogger().info("Body of the post: " + POST_BODY);
        Response response = apiTool.post(URI, POST_BODY, String.valueOf(Postfix.POSTS));
        String json = response.getBody().asString();
        AqualityServices.getLogger().info("Getting users info " + json);
        JsonPath jsonPath = new JsonPath(json);
        CheckResponseDataSteps checkResponseDataSteps = new CheckResponseDataSteps();
        checkResponseDataSteps.setPostObject(expectedPost, USER_ID_VALUE_FOR_POST, TITLE_VALUE, BODY_VALUE);
        checkResponseDataSteps.setPostObject(actualPost, jsonPath, USER_ID, TITLE, BODY);
        AqualityServices.getLogger().info("Comparing data of posts");
        Assert.assertNotNull(jsonPath.getString(ID), "id is empty");
        Assert.assertEquals(expectedPost, actualPost, "User data is wrong");
    }

    @Test(groups = {"CheckUsers"})
    public void testGetAllUsers() throws JSONException, NumberFormatException {
        ApiTool apiTool = new ApiTool();
        Response response = apiTool.getResponse(URI + Postfix.USERS);
        AqualityServices.getLogger().info("Getting users info");
        String json = response.getBody().asString();
        JsonTools jsonTools = new JsonTools();
        JSONObject jsonObject = jsonTools.getJSONObjectFromArray(json, 4);
        CheckResponseDataSteps checkResponseDataSteps = new CheckResponseDataSteps();
        checkResponseDataSteps.setUserObject(actualUser, jsonObject);
        AqualityServices.getLogger().info("Comparing data of user");
        Assert.assertEquals(expectedUser, actualUser, "User data is wrong");
    }

    @Test(groups = {"CheckUsers"})
    public void testCheckInfoUser5()
            throws JSONException, NumberFormatException {
        ApiTool apiTool = new ApiTool();
        Response response = apiTool.getResponse(URI + Postfix.USERS_BY_ID.format(NUMBER_OF_USER_ID_5));
        String json = response.getBody().asString();
        AqualityServices.getLogger().info("Getting users info " + json);
        JsonPath jsonPath = new JsonPath(json);
        CheckResponseDataSteps checkResponseDataSteps = new CheckResponseDataSteps();
        checkResponseDataSteps.setUserObject(actualUser, jsonPath);
        AqualityServices.getLogger().info("Comparing data of user");
        Assert.assertEquals(expectedUserForJsonObjects, actualUser, "User data is wrong");
    }
}