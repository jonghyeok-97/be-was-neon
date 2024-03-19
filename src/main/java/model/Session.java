package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Session {
    private static final Map<String, User> session = new HashMap<>();

    public static void createSession(final User user) {
        session.put(createSessionID(), user);
    }

    private static String createSessionID() {
        return "ab123123";
    }
}
