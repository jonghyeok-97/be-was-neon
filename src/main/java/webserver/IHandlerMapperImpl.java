package webserver;

import http.requestMessage.Request;
import webserver.handler.authentication.Login;
import webserver.handler.authentication.Logout;
import webserver.handler.authentication.UserList;
import webserver.file.StaticFile;
import webserver.handler.AuthenticationHandler;
import webserver.handler.GetHandler;
import webserver.handler.Handler;
import webserver.handler.RegisterHandler;

public class IHandlerMapperImpl implements IHandlerMapper {
    private final Request request;

    public IHandlerMapperImpl(Request request) {
        this.request = request;
    }

    @Override
    public Handler find() {
        String uri = request.getUri();
        if ("/login".equals(uri)) {
            return new AuthenticationHandler(new Login(request.getBodyKeyValue()));
        }
        if ("/logout".equals(uri)) {
            return new AuthenticationHandler(new Logout(request.getSessionID()));
        }
        if ("/user/list".equals(uri)) {
            return new AuthenticationHandler(new UserList(request.getSessionID()));
        }
        if ("/create".equals(uri)) {
            return new RegisterHandler(request.getBodyKeyValue());
        }
        return new GetHandler(new StaticFile(uri));
    }
}
