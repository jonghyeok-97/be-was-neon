package webserver.handler;

import http.Cookie;
import http.SessionManager;
import http.requestMessage.Request;
import http.requestMessage.RequestHeader;
import http.responseMessage.Response;
import http.responseMessage.StatusLine;
import webserver.path.BasicPath;

import java.util.Optional;

public class LogOutHandler implements Handler {
    private final RequestHeader header;

    LogOutHandler(RequestHeader header) {
        this.header = header;
    }

    @Override
    public Response handle() {
        final Optional<String> sessionID = header.getSessionId();

        return sessionID.map(sessionId -> {
            SessionManager.delete(sessionId);
            Cookie cookie = new Cookie(sessionId);
            cookie.set("Max-age", "0");
            return new Response.Builder(StatusLine.Found_302)
                    .location(BasicPath.HOME.getPath())
                    .setCookie(cookie)
                    .build();
        }).orElseThrow(() -> new IllegalArgumentException("404에러"));
    }
}
