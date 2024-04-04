package webserver;

import http.requestMessage.Request;
import webserver.authentication.Login;
import webserver.file.StaticFile;
import webserver.handler.*;
import webserver.model.User;

import java.util.List;
import java.util.Map;

public class HandlerFactoryImpl implements HandlerFactory{
    private final Request request;

    public HandlerFactoryImpl(Request request) {
        this.request = request;
    }

    @Override
    public List<Handler> createHandlers() {
        Map<String, String> userInfos = request.getBodyKeyValue();

        return List.of(
                new GetHandler(new StaticFile(request.getUri())),
                new AuthenticationHandler(new Login(createLoginUser(userInfos))),
                new LogOutHandler(request),
                new RegisterHandler(request.getBodyKeyValue()),
                new UserListHandler(request)
        );
    }

    private User createLoginUser(Map<String, String> userInfos) {
        final String userID = userInfos.get("userId");
        final String password = userInfos.get("password");
        return User.createUserForLogin(userID, password);
    }
}
