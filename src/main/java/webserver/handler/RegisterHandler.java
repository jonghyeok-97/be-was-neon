package webserver.handler;

import http.requestMessage.Request;
import http.responseMessage.Response;
import http.responseMessage.StatusLine;
import webserver.db.Database;
import webserver.model.User;
import webserver.model.UserInfo;
import webserver.path.BasicPath;

import java.util.Optional;

public class RegisterHandler implements Handler{
    private final Request request;

    RegisterHandler(Request request) {
        this.request = request;
    }

    public Response handle() {
        final Optional<String> optId = request.get(UserInfo.USER_ID);
        final Optional<String> optPassword = request.get(UserInfo.PASSWORD);
        final Optional<String> optName = request.get(UserInfo.NICKNAME);

        final User user = new User.Builder(optId.get(), optPassword.get())
                .name(optName.get())
                .build();
        Database.addUser(user);
        return new Response.Builder(StatusLine.Found_302)
                .location(BasicPath.HOME.getPath())
                .build();
    }
}
