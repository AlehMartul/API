package Steps;

import framework.tools.JsonTools;
import io.restassured.path.json.JsonPath;
import models.Post;
import models.User;
import org.json.JSONException;
import org.json.JSONObject;

public class CheckResponseDataSteps {

    public void setUserObject(User user, String nameValue, String usernameValue,
                              String emailValue, String addressValueForJsonObjects, String phoneValue,
                              String websiteValue, String companyValueForJsonObjects) {
        user.setAllFields(user, nameValue, usernameValue,
                emailValue, addressValueForJsonObjects, phoneValue, websiteValue, companyValueForJsonObjects);
    }

    public void setUserObject(User user, JSONObject jsonObject) {
        user.setAllFields(user, jsonObject.getString("name"), jsonObject.getString("username"),
                jsonObject.getString("email"), String.valueOf(jsonObject.get("address")), jsonObject.getString("phone"),
                jsonObject.getString("website"), String.valueOf(jsonObject.get("company")));
    }

    public void setUserObject(User user, JsonPath jsonPath) {
        user.setAllFields(user, jsonPath.getString("name"), jsonPath.getString("username"), jsonPath.getString("email"),
                jsonPath.getString("address"), jsonPath.getString("phone"), jsonPath.getString("website"),
                jsonPath.getString("company"));
    }

    public void setPostObject(Post post, String userId, String title, String body) {
        post.setAllFields(post, userId, title, body);
    }

    public void setPostObject(Post post, JsonPath jsonPath, String userId, String title, String body) {
        post.setAllFields(post, jsonPath.getString(userId), jsonPath.getString(title), jsonPath.getString(body));
    }

    public void setPostObject(Post post, JSONObject jsonObject, String id, String title, String body) {
        post.setAllFields(post, jsonObject.getInt(id), jsonObject.getString(title),
                jsonObject.getString(body));
    }

    public boolean isArraySortedById(int maxValue, String json, Post post, Post post_plus_1, String id, String title,
                                     String body) throws JSONException, NumberFormatException {
        JsonTools jsonTools = new JsonTools();
        boolean compare = false;
        for (int i = 0; i < maxValue; i++) {
            JSONObject jsonObject = jsonTools.getJSONObjectFromArray(json, i);
            JSONObject jsonObject1 = jsonTools.getJSONObjectFromArray(json, i + 1);
            setPostObject(post, jsonObject, id, title, body);
            setPostObject(post_plus_1, jsonObject1, id, title, body);
            if (post.getId() < post_plus_1.getId()) {
                compare = true;
            } else {
                compare = false;
            }
        }
        return compare;
    }
}

