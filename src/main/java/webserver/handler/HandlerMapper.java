package webserver.handler;

import http.requestMessage.Request;
import http.requestMessage.Uri;
import http.responseMessage.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class HandlerMapper {
    private final Map<Predicate<Uri>, Handler> handlerMapper = new HashMap<>();
    private final Uri uri;

    public HandlerMapper(final Request request) {
        this.uri = request.getUri();
        handlerMapper.put(uri -> request.isPost() && uri.isSame("/login"), new LoginHandler(request.getOptBody().get()));
        handlerMapper.put(uri -> request.isPost() && uri.isSame("/create"), new RegisterHandler(request.getOptBody().get()));
        handlerMapper.put(uri -> request.isPost() && uri.isSame("/logout"), new LogOutHandler(request.getHeader()));
        handlerMapper.put(uri -> request.isGet(), new GetHandler(uri));
    }

    public Handler find() {
        return handlerMapper.keySet().stream()
                .filter(predi -> predi.test(uri))
                .map(handlerMapper::get)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("404에러"));

        return handler.handle();
    }
}
