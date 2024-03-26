package db;

import model.Cookie;
import model.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionDB {
    private static final Map<Cookie, User> session = new ConcurrentHashMap<>();

    public static void add(Cookie cookie, User user) {
        session.put(cookie, user);
    }

    public static boolean has(Cookie cookie) {
        return session.containsKey(cookie);
    }
}
