package webserver.handler;

import http.responseMessage.Response;
import webserver.authentication.UserAuthentication;

public class AuthenticationHandler implements Handler{
    private final UserAuthentication userAuthentication;

    public AuthenticationHandler(UserAuthentication userAuthentication) {
        this.userAuthentication = userAuthentication;
    }

    @Override
    public Response handle() {
        if (userAuthentication.isPass()) {
            userAuthentication.handleSessionDB();
            return userAuthentication.getSuccessResponse();
        }
        return userAuthentication.getFailedResponse();
    }
}
