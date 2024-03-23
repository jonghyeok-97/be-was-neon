package webserver.handler;

import webserver.request.Request;
import webserver.response.Response;

import java.io.IOException;

public class RequestHandler {
    private final Request request;

    public RequestHandler(final Request request) {
        this.request = request;
    }

    public Response handle() throws IOException {
        if (request.has(PostHandler.Path.LOGIN)) {
            return new PostHandler(request).login();
        }
        if (request.has(PostHandler.Path.REGISTER)) {
            return new PostHandler(request).registerUser();
        }
        if (request.isGetRequest()) {
            return new GetHandler(request.getUri().get()).handle();
        }
        throw new IllegalArgumentException("404에러");
    }
}
