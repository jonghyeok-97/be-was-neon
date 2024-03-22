package webserver.handler;

import db.Database;
import model.Session;
import model.User;
import model.UserInfo;
import webserver.request.Request;
import webserver.response.Response;
import webserver.response.StatusLine;

import java.util.Optional;

public class PostHandler implements Handler{
    private final Request request;

    PostHandler(final Request request) {
        this.request = request;
    }

    // POST의 회원가입 로직에는 Id, password, name 은 무조건 있음.
    public Response registerUser() {
        final Optional<String> optId = request.get(UserInfo.USER_ID);
        final Optional<String> optPassword = request.get(UserInfo.PASSWORD);
        final Optional<String> optName = request.get(UserInfo.NICKNAME);

        final User user = new User.Builder(optId.get(), optPassword.get())
                .name(optName.get())
                .build();
        Database.addUser(user);
        return new Response.Builder(StatusLine.Found.getValue())
                .location("/index.html")
                .build();
    }

    // POST의 로그인 로직에는 id, pw는 무조건 있음.
    public Response login() {
        final Optional<String> optId = request.get(UserInfo.USER_ID);
        final Optional<String> optPassword = request.get(UserInfo.PASSWORD);
        final Optional<User> optUser = Database.findUserById(optId.get());

        return optUser.filter(user -> user.has(optPassword.get()))
                .map(user -> {
                    final String sessionId = Session.createSessionID();
                    Session.add(sessionId, user);
                    return new Response.Builder(StatusLine.Found.getValue())
                            .location("/index.html")
                            .cookie(sessionId)
                            .build();
                }).orElse(new Response.Builder(StatusLine.Found.getValue()).build());
    }
}
