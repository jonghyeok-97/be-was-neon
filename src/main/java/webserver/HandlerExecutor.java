package webserver;

import http.responseMessage.Response;
import webserver.IHandlerMapper;

public class HandlerExecutor {
    private final IHandlerMapper IHandlerMapper;

    public HandlerExecutor(IHandlerMapper IHandlerMapper) {
        this.IHandlerMapper = IHandlerMapper;
    }


    public Response execute() {
        return IHandlerMapper.find().handle();
    }
}
