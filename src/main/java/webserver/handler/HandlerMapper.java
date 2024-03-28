package webserver.handler;

import http.requestMessage.Uri;
import http.requestMessage.Request;
import http.responseMessage.Response;

import java.io.IOException;

public class HandlerMapper {
    private final Request request;

    public HandlerMapper(final Request request) {
        this.request = request;
    }

    public Response handle() throws IOException {
        final Uri uri = request.getUri();
        if (uri.isSame("/login")) {
            return new LoginHandler(request.getOptBody().get()).handle();
        }
        if (uri.isSame("/create")) {
            return new RegisterHandler(request.getOptBody().get()).handle();
        }
        if (uri.isSame("/logout")) {
            return new LogOutHandler(request.getHeader()).handle();
        }
        if (request.isGet()) {
            return new GetHandler(uri).handle();
        }
        throw new IllegalArgumentException("404에러");
    }
}
