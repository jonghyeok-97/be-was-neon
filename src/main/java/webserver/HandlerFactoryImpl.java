package webserver;

import http.requestMessage.Request;
import webserver.file.StaticFile;
import webserver.handler.*;

import java.util.List;

public class HandlerFactoryImpl implements HandlerFactory{
    private final Request request;

    public HandlerFactoryImpl(Request request) {
        this.request = request;
    }

    @Override
    public List<Handler> createHandlers() {
        return List.of(
                new GetHandler(new StaticFile(request.getUri())),
                new LoginHandler(request),
                new LogOutHandler(request),
                new RegisterHandler(request.getBodyKeyValue()),
                new UserListHandler(request)
        );
    }
}
