package webserver.handler;

import webserver.path.PostPath;
import http.requestMessage.Request;
import http.responseMessage.Response;

import java.io.IOException;

public class HandlerMapper {
    private final Request request;

    public HandlerMapper(final Request request) {
        this.request = request;
    }

    public Response handle() throws IOException {
        if (request.has(PostPath.LOGIN)) {
            return new LoginHandler(request).handle();
        }
        if (request.has(PostPath.REGISTER)) {
            return new RegisterHandler(request).handle();
        }
        if (request.has(PostPath.LOGOUT)) {
            return new LogoutHandler(request).handle();
        }
        if (request.isGet()) {
            return new GetHandler(request.getUri().get()).handle();
        }
        throw new IllegalArgumentException("404에러");
    }
}
