package webserver.handler;

import http.requestMessage.Request;
import http.responseMessage.Response;
import http.responseMessage.StatusLine;
import webserver.db.Database;
import webserver.model.User;
import webserver.path.BasicPath;

import java.util.Map;

public class RegisterHandler implements Handler {
    private final Map<String, String> bodyKeyValues;
    private final Request request;

    public RegisterHandler(Request request) {
        this.bodyKeyValues = request.getBodyKeyValue();
        this.request = request;
    }

    @Override
    public boolean isExecute() {
        return request.isPost() && request.corresponds("/create");
    }

    public Response handle() {
        final String userId = bodyKeyValues.get("userId");
        final String password = bodyKeyValues.get("password");
        final String name = bodyKeyValues.get("name");

        final User user = User.createUserForRegister(userId, password, name);
        Database.addUser(user);

        return new Response.Builder(StatusLine.Found_302)
                .location(BasicPath.HOME.getPath())
                .build();
    }
}
