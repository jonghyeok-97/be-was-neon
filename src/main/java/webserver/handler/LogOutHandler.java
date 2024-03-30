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
    private final Request request;

    public LogOutHandler(Request request) {
        this.header = request.getHeader();
        this.request = request;
    }

    @Override
    public boolean isExecute() {
        return request.isPost() && request.corresponds("/logout");
    }

    @Override
    public Response handle() {
        final Optional<String> optSessionID = header.getSessionID();

        return optSessionID.map(sessionId -> {
            SessionManager.delete(sessionId);
            Cookie cookie = new Cookie();
            cookie.set("SID", sessionId).set("Max-age", "0");
            return createRedirectResponse(cookie);
        }).orElseThrow(() -> new IllegalArgumentException("404에러"));
    }

    private Response createRedirectResponse(Cookie cookie) {
        return new Response.Builder(StatusLine.Found_302)
                .location(BasicPath.HOME.getPath())
                .setCookie(cookie)
                .build();
    }

}
