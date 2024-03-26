package db;

import model.Cookie;
import model.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionDB {
    private static final Map<Cookie, User> session = new ConcurrentHashMap<>();

    public static void add(final Cookie cookie, final User user) {
        session.put(cookie, user);
    }
}
