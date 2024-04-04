package webserver.handler.authentication;

import http.Cookie;
import http.Property;
import http.SessionDB;
import http.responseMessage.Response;
import http.responseMessage.StatusLine;

import java.util.List;

public class Logout implements UserAuthentication {
    private static final String HOME = "/index.html";

    private final String sessionID;

    public Logout(final String sessionID) {
        this.sessionID = sessionID;
    }

    @Override
    public boolean isPass() {
        return SessionDB.hasSessionID(sessionID);
    }

    @Override
    public void handleSessionDB() {
        SessionDB.delete(sessionID);
    }

    @Override
    public Response getSuccessResponse() {
        return new Response.Builder(StatusLine.Found_302)
                .location(HOME)
                .setCookie(createCookie())
                .build();
    }

    @Override
    public Response getFailedResponse() {
        throw new IllegalArgumentException("404에러");
    }

    private Cookie createCookie() {
        return new Cookie(List.of(new Property("SID", sessionID), new Property("Max-age", "0")));
    }

}
