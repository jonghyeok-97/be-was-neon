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
        if (request.isLogin()) {
            return new PostHandler(request).login();
        }
        if (request.isRegister()) {
            return new PostHandler(request).registerUser();
        }
        return new GetHandler(request).handle();
    }
}
