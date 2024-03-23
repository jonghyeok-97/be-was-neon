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
        // GET요청일 때만 URI를 받아서 요청을 처리하는데, 너무 구체적으로 로직인 것 같다. 어떻게 추상화 시켜야 하지.,.
        if (request.isGetRequest()) {
            return new GetHandler(request.getUri().get()).handle();
        }
        throw new IllegalArgumentException("404에러");
    }
}
