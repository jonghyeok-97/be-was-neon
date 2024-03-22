package model;

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

    // 유저 Id와 pw를 필수로 받게 하고 싶어서 builder를 사용했지만
    // 팩토리메소드를 사용하는 선택이 더 좋았겠다.
    // builder는 매개변수 많을 떄 사용하자!
    static public class Builder {
        private String userId;
        private String password;
        private String name;

        public Builder(String userId, String password) {
            this.userId = userId;
            this.password = password;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public User build() {
            return new User(userId, password, name);
        }
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public boolean isSame(final User other) {
        return Objects.equals(userId, other.userId) && Objects.equals(password, other.password);
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", password=" + password + ", name=" + name + "]";
    }
}
