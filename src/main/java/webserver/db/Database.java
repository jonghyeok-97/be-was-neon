package webserver.db;

import webserver.model.User;

import java.util.*;

public class Database {
    private static Map<String, User> users = new HashMap<>();

    public static void addUser(User user) {
        Objects.requireNonNull(user, "user의 값은 " + user + " 이 될 수 없습니다.");
        users.put(user.getUserId(), user);
    }

    public static Optional<User> findUserById(String userId) {
        return Optional.ofNullable(users.get(userId));
    }

    public static Collection<User> findAll() {
        return users.values();
    }
}
