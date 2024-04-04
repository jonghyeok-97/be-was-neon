package webserver.handler;

import http.responseMessage.Response;
import http.responseMessage.StatusLine;
import webserver.db.Database;
import webserver.model.User;

import java.util.Map;

public class RegisterHandler implements Handler {
    private static final String HOME = "/index.html";

    private final Map<String, String> userInfos;

    public RegisterHandler(Map<String, String> userInfos) {
        this.userInfos = userInfos;
    }

    public Response start() {
        User user = createUser();
        Database.addUser(user);

        return new Response.Builder(StatusLine.Found_302)
                .location(HOME)
                .build();
    }

    private User createUser() {
        final String userId = userInfos.get("userId");
        final String password = userInfos.get("password");
        final String name = userInfos.get("name");

        return User.createUserForRegister(userId, password, name);
    }
}
