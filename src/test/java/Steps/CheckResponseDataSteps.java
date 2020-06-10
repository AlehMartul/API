package Steps;

import aquality.selenium.browser.AqualityServices;
import framework.tools.JsonTools;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import models.Post;
import models.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CheckResponseDataSteps {

    private static final String USER_ID = "userId";
    private static final String TITLE = "title";
    private static final String BODY = "body";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String USERNAME = "username";
    private static final String ADDRESS = "address";
    private static final String EMAIL = "email";
    private static final String PHONE = "phone";
    private static final String WEBSITE = "website";
    private static final String COMPANY = "company";

    public static void setUserObject(User user, String nameValue, String usernameValue,
                                     String emailValue, String addressValueForJsonObjects, String phoneValue,
                                     String websiteValue, String companyValueForJsonObjects) {
        user.setAllFields(user, nameValue, usernameValue,
                emailValue, addressValueForJsonObjects, phoneValue, websiteValue, companyValueForJsonObjects);
    }

    public static void setUserObject(User user, Response response, int number) {
        String json = response.getBody().asString();
        JsonTools jsonTools = new JsonTools();
        JSONObject jsonObject = jsonTools.getJSONObjectFromArray(json, number);
        user.setAllFields(user, jsonObject.getString(NAME), jsonObject.getString(USERNAME),
                jsonObject.getString(EMAIL), String.valueOf(jsonObject.get(ADDRESS)), jsonObject.getString(PHONE),
                jsonObject.getString(WEBSITE), String.valueOf(jsonObject.get(COMPANY)));
    }

    public static void setUserObject(User user, Response response) {
        String json = response.getBody().asString();
        AqualityServices.getLogger().info("Getting users info " + json);
        JsonPath jsonPath = new JsonPath(json);
        user.setAllFields(user, jsonPath.getString(NAME), jsonPath.getString(USERNAME), jsonPath.getString(EMAIL),
                jsonPath.getString(ADDRESS), jsonPath.getString(PHONE), jsonPath.getString(WEBSITE),
                jsonPath.getString(COMPANY));
    }

    public static void setPostObject(Post post, int userId, String title, String body) {
        post.setFieldsWithoutID(post, userId, title, body);
    }

    public static void setPostObject(Post post, int id, int userId, String title, String body) {
        post.setAllFields(post, id, userId, title, body);
    }

    public static void setPostObject(Post post, JsonPath jsonPath) {
        post.setFieldsWithoutID(post, jsonPath.getInt(USER_ID), jsonPath.getString(TITLE),
                replaceLineBreak(jsonPath.getString(BODY)));
    }

    public static String replaceLineBreak(String initial) {
        String replaceString = initial.replaceAll("(\r\n|\n)", " ");
        return replaceString;
    }

    public static void setPostObject(Post post, JSONObject jsonObject) {
        post.setAllFields(post, jsonObject.getInt(ID), jsonObject.getInt(USER_ID), jsonObject.getString(TITLE),
                replaceLineBreak(jsonObject.getString(BODY)));
    }

    public static boolean isArrayOfPostsSortedById(JSONArray jsonArray, Post post, Post post_plus_1)
            throws JSONException, NumberFormatException {
        int count = 0;
        boolean compare = false;
        for (int i = 0; i < jsonArray.length() - 1; i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            JSONObject jsonObject1 = jsonArray.getJSONObject(i + 1);
            setPostObject(post, jsonObject);
            setPostObject(post_plus_1, jsonObject1);
            if (post.getId() >= post_plus_1.getId()) {
                count++;
            }
            if (count == 0) {
                compare = true;
            }
        }
        return compare;
    }
}
