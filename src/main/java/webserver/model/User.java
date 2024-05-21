package webserver.model;

import java.util.Objects;

public class User {
    private final String userId;
    private final String password;
    private final String name;

    private User(String userId, String password, String name) {
        this.userId = userId;
        this.password = password;
        this.name = name;
    }

    public static User createUserForRegister(String userId, String password, String name) {
        return new User(userId, password, name);
    }

    public static User createUserForLogin(String userId, String password) {
        return new User(userId, password, "");
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public boolean isSame(final User other) {
        return Objects.equals(this.password, password) && Objects.equals(this.userId, other.userId);
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", password=" + password + ", name=" + name + "]";
    }
}
