package webserver.authentication;

import http.Cookie;
import http.Property;
import http.SessionManager;
import http.responseMessage.Response;
import http.responseMessage.StatusLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.db.Database;
import webserver.file.AbstractFile;
import webserver.file.DynamicFile;
import webserver.model.User;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Login implements UserAuthentication {
    public static final String LOGIN_SUCCESS_PATH = "/main/index.html";
    private static final String LOGIN_FAILED_PATH = "/login/failed_index.html";
    private static final Logger logger = LoggerFactory.getLogger(Login.class);

    private final User user;
    private final String sessionID;

    public Login(User user) {
        this.user = user;
        this.sessionID = createSessionID();
    }

    private String createSessionID() {
        String sid = UUID.randomUUID().toString();
        while (SessionManager.hasSessionID(sid)) {
            sid = UUID.randomUUID().toString();
        }
        return sid;
    }

    @Override
    public boolean isPass() {
        Optional<User> optUser = Database.findUserById(user.getUserId());
        return optUser.filter(found -> found.equals(user)).isPresent();
    }

    @Override
    public void handleSessionDB() {
        SessionManager.add(sessionID, user);
    }

    @Override
    public Response getSuccessResponse() {
        AbstractFile abstractFile = new DynamicFile(LOGIN_SUCCESS_PATH, user.getName());
        try {
            return new Response.Builder(StatusLine.OK_200)
                    .contentType(abstractFile.findSubTypeOfMIME())
                    .contentLength(abstractFile.read())
                    .setCookie(createCookie())
                    .build();
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new IllegalArgumentException("500에러");
        }
    }

    @Override
    public Response getFailedResponse() {
        return new Response.Builder(StatusLine.Found_302)
                .location(LOGIN_FAILED_PATH)
                .build();
    }

    private Cookie createCookie() {
        return new Cookie(List.of(new Property("SID", sessionID)));
    }
}
