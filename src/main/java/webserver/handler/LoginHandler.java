package webserver.handler;

import http.Cookie;
import http.SessionManager;
import http.requestMessage.RequestBody;
import http.responseMessage.Response;
import http.responseMessage.StatusLine;
import webserver.db.Database;
import webserver.model.User;

import java.util.Optional;

public class LoginHandler extends UserInfoHandler{
    private static final String LOGIN_FAILED_PATH = "/login/failed_index.html";
    private static final String LOGIN_SUCCESS_PATH = "/main/index.html";

    LoginHandler(final RequestBody body) {
        super(body);
    }

    public Response handle() {
        final String userId = userInfos.get("userId");
        final String password = userInfos.get("password");
        final Optional<User> optUser = Database.findUserById(userId);

        return optUser.filter(user -> user.hasPassword(password))
                .map(user -> {
                    final String sessionID = SessionManager.createSessionID();
                    final Cookie cookie = new Cookie(sessionID);
                    SessionManager.add(sessionID, user);
                    return createLoginSuccessResponse(cookie);
                }).orElse(createLoginFailedResponse());
    }

    private Response createLoginSuccessResponse(Cookie cookie) {
        return new Response.Builder(StatusLine.Found_302)
                .location(LOGIN_SUCCESS_PATH)
                .setCookie(cookie)
                .build();
    }

    private Response createLoginFailedResponse() {
        return new Response.Builder(StatusLine.Found_302)
                .location(LOGIN_FAILED_PATH)
                .build();
    }
}
