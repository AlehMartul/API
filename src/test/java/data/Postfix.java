package data;

public enum Postfix {
    POSTS("/posts"),
    POSTS99("/posts/99"),
    POSTS150("/posts/150"),
    POSTS_BY_ID("/posts/%s"),
    USERS5("/users/5"),
    USERS_BY_ID("/users/%s"),
    USERS("/users");

    private String title;

    Postfix(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }

    public String format(Object params){
        return String.format(title, params);
    }
}
