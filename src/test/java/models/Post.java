package models;

import aquality.selenium.browser.AqualityServices;

public class Post {

    private int id;
    private int userId;
    private String title;
    private String body;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setUserBody(String body) {
        this.body = body;
    }

    public void setFieldsWithoutID(Post post, int userId, String title, String body) {
        AqualityServices.getLogger().info("Setting all fields");
        post.setUserId(userId);
        post.setTitle(title);
        post.setUserBody(body);
    }

    public void setAllFields(Post post, int id, int userId, String title, String body) {
        AqualityServices.getLogger().info("Setting all fields");
        post.setId(id);
        post.setUserId(userId);
        post.setTitle(title);
        post.setUserBody(body);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + title.hashCode() + userId;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        Post post1 = (Post) obj;
        return (userId == post1.userId || (userId != 0 && userId == (post1.getUserId())))
                && (title == post1.title || (title != null && title.equals(post1.getTitle()))) &&
                (body == post1.body || (body != null && body.equals(post1.getBody())));
    }
}
