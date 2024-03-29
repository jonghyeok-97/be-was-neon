package webserver.handler;

import http.requestMessage.RequestBody;
import http.responseMessage.Response;
import http.responseMessage.StatusLine;
import webserver.db.Database;
import webserver.model.User;
import webserver.path.BasicPath;

public class RegisterHandler implements Handler {
    private final UserInfo userInfo;

    RegisterHandler(RequestBody body) {
        this.userInfo = new UserInfo(body);
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
