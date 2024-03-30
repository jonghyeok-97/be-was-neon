package webserver.handler;

import http.requestMessage.Request;
import http.responseMessage.Response;
import http.responseMessage.StatusLine;
import webserver.db.Database;
import webserver.model.User;
import webserver.path.BasicPath;

public class RegisterHandler implements Handler {
    private final UserInfo userInfo;
    private final Request request;

    public RegisterHandler(Request request) {
        this.userInfo = request.getOptBody().map(body -> new UserInfo(body)).orElse(null);
        this.request = request;
    }

    @Override
    public boolean isExecute() {
        return request.isPost() && request.corresponds("/create");
    }

    public Response handle() {
        final String userId = userInfo.get("userId");
        final String password = userInfo.get("password");
        final String name = userInfo.get("name");

        final User user = User.createUserForRegister(userId, password, name);
        Database.addUser(user);

        return new Response.Builder(StatusLine.Found_302)
                .location(BasicPath.HOME.getPath())
                .build();
    }
}
