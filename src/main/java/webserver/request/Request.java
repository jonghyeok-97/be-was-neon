package webserver.request;

import webserver.Response;

import java.io.IOException;

public interface Request {

    Response execute() throws IOException;
}
