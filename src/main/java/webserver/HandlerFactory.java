package webserver;

import webserver.handler.Handler;

import java.util.List;

public interface HandlerFactory {

    List<Handler> createHandlers();
}
