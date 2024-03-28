package http;

import webserver.model.User;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {
    private static final Map<String, User> sessionStorage = new ConcurrentHashMap<>();

    private SessionManager(){}

    public static String createSessionID() {
        String sid = UUID.randomUUID().toString();
        while (sessionStorage.containsKey(sid)) {
            sid = UUID.randomUUID().toString();
        }
        return sid;
    }

    public static void add(String sid, User user) {
        sessionStorage.put(sid, user);
    }

    public static void delete(String sessionId) {
        sessionStorage.remove(sessionId);
    }
}
