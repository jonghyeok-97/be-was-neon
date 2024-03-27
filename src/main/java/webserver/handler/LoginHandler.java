package webserver.handler;

import http.Cookie;
import http.SessionManager;
import http.requestMessage.Request;
import http.responseMessage.Response;
import http.responseMessage.StatusLine;
import webserver.db.Database;
import webserver.model.User;
import webserver.model.UserInfo;

import java.util.Optional;

public class LoginHandler implements Handler{
    private static final String LOGIN_FAILED_PATH = "/login/failed_index.html";
    private static final String LOGIN_SUCCESS_PATH = "/main/index.html";

    private final Request request;

    LoginHandler(Request request) {
        this.request = request;
    }

    public Response handle() {
        final Optional<String> optId = request.get(UserInfo.USER_ID);
        final Optional<String> optPassword = request.get(UserInfo.PASSWORD);
        final Optional<User> optUser = Database.findUserById(optId.get());

        return optUser.filter(user -> user.hasPassword(optPassword.get()))
                .map(user -> {
                    final String sessionID = SessionManager.createSessionID();
                    Cookie cookie = new Cookie(sessionID);
                    SessionManager.add(sessionID, user);
                    return new Response.Builder(StatusLine.Found_302)
                            .location(LOGIN_SUCCESS_PATH)
                            .setCookie(cookie)
                            .build();
                }).orElse(createLoginFailedMessage());
    }

    private Response createLoginFailedMessage() {
        return new Response.Builder(StatusLine.Found_302)
                .location(LOGIN_FAILED_PATH)
                .build();
    }
}
