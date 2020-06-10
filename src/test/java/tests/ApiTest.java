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
import org.testng.annotations.BeforeTest;
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
    private static final String URI = new ReadPropertyTool(RESOURCES_PATH, PROPERTIES_FILE_NAME).getProperty("mainUrl");
    private static final String USER_ID = "userId";
    private static final String TITLE = "title";
    private static final String BODY = "body";
    private static final String ID = "id";


    @BeforeTest()
    @Parameters({"nameValue", "usernameValue", "emailValue", "addressValue", "phoneValue", "websiteValue",
            "companyValue", "addressValueForJsonObject", "companyValueForJsonObject"})
    public void setUp(String nameValue, String usernameValue, String emailValue,
                      String addressValueForJsonObjects, String phoneValue, String websiteValue,
                      String companyValueForJsonObjects, String addressValue, String companyValue) {
        CheckResponseDataSteps.setUserObject(expectedUserForJsonObjects, nameValue,
                usernameValue, emailValue, addressValueForJsonObjects, phoneValue, websiteValue,
                companyValueForJsonObjects);
        CheckResponseDataSteps.setUserObject(expectedUser, nameValue, usernameValue, emailValue,
                addressValue, phoneValue, websiteValue, companyValue);
    }

    @Test
    public void testGetAllPosts() throws JSONException, NumberFormatException {
        ApiTool apiTool = new ApiTool();
        Response response = apiTool.get(URI + Postfix.POSTS);
        String allPosts = response.getBody().asString();
        AqualityServices.getLogger().info("Getting all posts " + allPosts);
        String json = response.getBody().asString();
        Assert.assertTrue(CheckResponseDataSteps.isArrayOfPostsSortedById(JsonTools.getJSONArrayFromString(json), post,
                post_plus_1));
    }

    @Test
    @Parameters({"post99", "valueOfUserId", "titleValue", "bodyValue"})
    public void testGetPostByIndex(String post99, String valueOfUserId, String titleValue, String bodyValue)
            throws JSONException {
        ApiTool apiTool = new ApiTool();
        Response response = apiTool.get(URI + Postfix.POSTS_BY_ID.format(post99));
        String json = response.getBody().asString();
        AqualityServices.getLogger().info("Getting posts N " + post99 + " " + json);
        JSONObject jsonObject = new JSONObject(json);
        CheckResponseDataSteps.setPostObject(actualPost, jsonObject);
        CheckResponseDataSteps.setPostObject(expectedPost, Integer.parseInt(post99), Integer.parseInt(valueOfUserId),
                titleValue, bodyValue);
        AqualityServices.getLogger().info("Checking " + TITLE);
        Assert.assertNotNull(actualPost.getTitle(), "title is empty");
        AqualityServices.getLogger().info("Checking " + BODY);
        Assert.assertNotNull(actualPost.getBody(), "body is empty");
        AqualityServices.getLogger().info("Checking " + USER_ID + " and " + ID);
        Assert.assertEquals(actualPost, expectedPost, "ID and User ID are wrong");
    }

    @Test
    @Parameters("post150")
    public void testUnExistedPost(String post150) throws JSONException {
        ApiTool apiTool = new ApiTool();
        Response response = apiTool.get(URI + Postfix.POSTS_BY_ID.format(post150), HttpStatus.SC_NOT_FOUND);
        String json = response.getBody().asString();
        AqualityServices.getLogger().info("Getting unexisted posts " + json);
        Assert.assertEquals("{}", json, "Body of post is not Null");
        AqualityServices.getLogger().info("Checking status code");
    }

    @Test
    @Parameters({"postBody", "userIdValueForPost", "titleValue", "bodyValue"})
    public void testMakePost(String postBody, String userIdValueForPost, String titleValue, String bodyValue)
            throws JSONException, NumberFormatException {
        ApiTool apiTool = new ApiTool();
        AqualityServices.getLogger().info("Body of the post: " + postBody);
        Response response = apiTool.post(URI, postBody, String.valueOf(Postfix.POSTS));
        String json = response.getBody().asString();
        AqualityServices.getLogger().info("Getting users info " + json);
        JsonPath jsonPath = new JsonPath(json);
        CheckResponseDataSteps.setPostObject(expectedPost, Integer.parseInt(userIdValueForPost), titleValue, bodyValue);
        CheckResponseDataSteps.setPostObject(actualPost, jsonPath);
        AqualityServices.getLogger().info("Comparing data of posts");
        Assert.assertNotNull(jsonPath.getString(ID), "id is empty");
        Assert.assertEquals(expectedPost, actualPost, "User data is wrong");
    }

    @Test
    @Parameters("indexOfUserId5")
    public void testGetAllUsers(String indexOfUserId5) throws JSONException, NumberFormatException {
        ApiTool apiTool = new ApiTool();
        Response response = apiTool.get(URI + Postfix.USERS);
        AqualityServices.getLogger().info("Getting users info");
        CheckResponseDataSteps.setUserObject(actualUser, response, Integer.parseInt(indexOfUserId5));
        AqualityServices.getLogger().info("Comparing data of user");
        Assert.assertEquals(expectedUser, actualUser, "User data is wrong");
    }

    @Test
    @Parameters("numberOfUserId5")
    public void testCheckInfoUser5(String numberOfUserId5)
            throws JSONException, NumberFormatException {
        ApiTool apiTool = new ApiTool();
        Response response = apiTool.get(URI + Postfix.USERS_BY_ID.format(numberOfUserId5));
        CheckResponseDataSteps.setUserObject(actualUser, response);
        AqualityServices.getLogger().info("Comparing data of user");
        Assert.assertEquals(expectedUserForJsonObjects, actualUser, "User data is wrong");
    }
}