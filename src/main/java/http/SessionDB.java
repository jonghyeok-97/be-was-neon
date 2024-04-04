package http;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SessionDB {
    private static final Map<String, String> sessionStorage = new ConcurrentHashMap<>();

    private SessionDB(){}

    public static String createSessionID() {
        String sid = UUID.randomUUID().toString();
        while (sessionStorage.containsKey(sid)) {
            sid = UUID.randomUUID().toString();
        }
        return sid;
    }

    public static void add(String sid, String userID) {
        sessionStorage.put(sid, userID);
    }

    public static void delete(String sessionId) {
        sessionStorage.remove(sessionId);
    }

    public static boolean hasSessionID(String id) {
        return sessionStorage.containsKey(id);
    }
}
