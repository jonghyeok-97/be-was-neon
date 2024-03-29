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
    private final FileHandler fileHandler = new FileHandler(LOGIN_SUCCESS_PATH);

    LoginHandler(RequestBody body) {
        super(body);
    }

    public Response handle() {
        final String userId = userInfos.get("userId");
        final String password = userInfos.get("password");
        Optional<User> optUser = Database.findUserById(userId);

        return optUser.filter(user -> user.hasPassword(password))
                .map(user -> {
                    final String sessionID = SessionManager.createSessionID();
                    SessionManager.add(sessionID, user);
                    final Cookie cookie = setCookie(sessionID);
                    final String subType = fileHandler.findSubTypeOfMIME();
                    final byte[] data = fileHandler.makeDynamicHTMLWith(user.getName());
                    return createLoginSuccessResponse(cookie, data, subType);
                }).orElse(createLoginFailedResponse());
    }

    private Response createLoginSuccessResponse(Cookie cookie, final byte[] data, final String subType) {
        return new Response.Builder(StatusLine.OK_200)
                .contentType(subType)
                .contentLength(data)
                .setCookie(cookie)
                .build();
    }

    private Response createLoginFailedResponse() {
        return new Response.Builder(StatusLine.Found_302)
                .location(LOGIN_FAILED_PATH)
                .build();
    }

    private Cookie setCookie(final String sessionID) {
        Cookie cookie = new Cookie();
        cookie.set("SID", sessionID);
        return cookie;
    }
}
