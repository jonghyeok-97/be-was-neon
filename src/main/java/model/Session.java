package model;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Session {
    private static final Map<String, User> session = new HashMap<>();

    public static void add(final String sessionId, final User user) {
        session.put(sessionId, user);
    }

    public static String createSessionID() {
        return UUID.randomUUID().toString();
    }
}
