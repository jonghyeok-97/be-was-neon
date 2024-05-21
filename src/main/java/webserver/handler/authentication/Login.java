package webserver.handler.authentication;

import http.Cookie;
import http.Property;
import http.SessionDB;
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
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class Login implements UserAuthentication {
    private static final Logger logger = LoggerFactory.getLogger(Login.class);

    private static final String LOGIN_SUCCESS_PATH = "/main/index.html";
    private static final String LOGIN_FAILED_PATH = "/login/failed_index.html";

    private final String sessionID;
    private final User user;  // 요청한 유저
    private Optional<User> foundUser; // DB에서 찾은 유저

    public Login(Map<String, String> userInfos) {
        this.user = createLoginUser(userInfos);
        this.sessionID = createSessionID();
    }

    private User createLoginUser(Map<String, String> userInfos) {
        final String userID = userInfos.get("userId");
        final String password = userInfos.get("password");
        return User.createUserForLogin(userID, password);
    }

    private String createSessionID() {
        String sid = UUID.randomUUID().toString();
        while (SessionDB.hasSessionID(sid)) {
            sid = UUID.randomUUID().toString();
        }
        return sid;
    }

    @Override
    public boolean isPass() {
        foundUser = Database.findUserById(user.getUserId());
        return foundUser.filter(found -> found.isSame(user)).isPresent();
    }

    @Override
    public void handleSessionDB() {
        SessionDB.add(sessionID, user.getUserId());
    }

    @Override
    public Response getSuccessResponse() {
        AbstractFile abstractFile = new DynamicFile(LOGIN_SUCCESS_PATH, foundUser.get().getName());
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
