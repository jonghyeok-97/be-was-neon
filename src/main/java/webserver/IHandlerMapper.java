package webserver;

import webserver.handler.Handler;

public interface IHandlerMapper {

    Handler findHandler();
}
