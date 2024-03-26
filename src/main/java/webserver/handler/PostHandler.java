package webserver.handler;

import db.Database;
import model.Session;
import model.User;
import model.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.path.BasicPath;
import webserver.request.Request;
import webserver.response.Response;
import webserver.response.StatusLine;

import java.util.Optional;

public class PostHandler implements Handler{
    private static final Logger logger = LoggerFactory.getLogger(PostHandler.class);

    private static final String LOGIN_FAILED_PATH = "/login/failed_index.html";
    private static final String LOGIN_SUCCESS_PATH = "/main/index.html";

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
        return new Response.Builder(StatusLine.Found_302)
                .location(BasicPath.HOME.getPath())
                .build();
    }

    // POST의 로그인 로직에는 id, pw는 무조건 있음.
    public Response login() {
        final Optional<String> optId = request.get(UserInfo.USER_ID);
        final Optional<String> optPassword = request.get(UserInfo.PASSWORD);
        final Optional<User> optUser = Database.findUserById(optId.get());

        return optUser.filter(user -> user.hasPassword(optPassword.get()))
                .map(user -> {
                    final String sessionId = Session.createSessionID();
                    Session.add(sessionId, user);
                    logger.debug("로그인 성공!");
                    return new Response.Builder(StatusLine.Found_302)
                            .location(LOGIN_SUCCESS_PATH)
                            .cookie(sessionId)
                            .build();
                }).orElse(createLoginFailedMessage());
    }

    private Response createLoginFailedMessage() {
        return new Response.Builder(StatusLine.Found_302)
                .location(LOGIN_FAILED_PATH)
                .build();
    }
}
