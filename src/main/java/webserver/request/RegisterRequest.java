package webserver.request;

import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.Response;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RegisterRequest implements Request {
    private static final Logger logger = LoggerFactory.getLogger(RegisterRequest.class);

    private final String path;

    public RegisterRequest(final String path) {
        this.path = path;
    }

    public Response execute() throws IOException {
        // /create?userID~~ 에서 ?를 기준으로 분리
        final String[] paths = path.split("\\?");

        // userId=321&name=111&password=11 을 &로 분리
        final String[] userRegisterInfo = paths[1].split("&");

        // userId=321 를 = 로 분리해서 list생성
        final List<String> signUpInfo = Arrays.stream(userRegisterInfo)
                .map(info -> info.split("=")[1])
                .collect(Collectors.toList());

        // id, name, password 순
        final User user = new User(signUpInfo.get(0), signUpInfo.get(2), signUpInfo.get(1));
        logger.debug("저장되는 User : {}", user);
        Database.addUser(user);

        return new Response(new HtmlRequest().getBaseHtmlFile());
    }

}
