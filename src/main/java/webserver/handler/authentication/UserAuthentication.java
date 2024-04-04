package webserver.handler.authentication;

import http.responseMessage.Response;

public interface UserAuthentication {

    boolean isPass();

    void handleSessionDB();

    Response getSuccessResponse();

    Response getFailedResponse();
}