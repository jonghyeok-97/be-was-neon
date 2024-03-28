package webserver.handler;

import http.requestMessage.RequestBody;
import http.responseMessage.Response;
import http.responseMessage.StatusLine;
import webserver.db.Database;
import webserver.model.User;
import webserver.path.BasicPath;

public class RegisterHandler extends UserInfoHandler {

    RegisterHandler(RequestBody body) {
        super(body);
    }

    public Response handle() {
        final String userId = userInfos.get("userId");
        final String password = userInfos.get("password");
        final String name = userInfos.get("name");

        final User user = new User.Builder(userId, password)
                .name(name)
                .build();
        Database.addUser(user);
        return new Response.Builder(StatusLine.Found_302)
                .location(BasicPath.HOME.getPath())
                .build();
    }
}
