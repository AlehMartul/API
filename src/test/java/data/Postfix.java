package data;

public enum Postfix {
    POSTS("/posts"),
    POSTS99("/posts/99"),
    POSTS150("/posts/150"),
    USERS5("/users/5"),
    USERS("/users");

    private String title;

    Postfix(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
