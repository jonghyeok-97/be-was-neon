package webserver;

import http.responseMessage.Response;
import webserver.handler.Handler;

public class HandlerExecutor {
    private final IHandlerMapper IHandlerMapper;

    public HandlerExecutor(IHandlerMapper IHandlerMapper) {
        this.IHandlerMapper = IHandlerMapper;
    }


    public Response execute() {
        Handler handler = IHandlerMapper.findHandler();
        return handler.start();
    }
}
