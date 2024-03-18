package webserver.request.body;

import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;

public class RequestBody {
    private final Logger logger = LoggerFactory.getLogger(RequestBody.class);
    private final String queryParameters;

    public RequestBody(final String _body) {
        queryParameters = _body;
    }

    public void addUserToDB() {
        final String[] querys = getQuerys(queryParameters);
        final List<String> userInfo = getUserInfo(querys);
        final User user = createUser(userInfo);
        logger.debug("저장되는 User : {}", user);
        Database.addUser(user);
    }

    private String[] getQuerys(final String queryParmas) {
        try {
            return queryParmas.split("&");
        } catch (PatternSyntaxException e) {
            logger.error(e.getMessage());
            throw new IllegalArgumentException("404에러");
        }
    }

    private List<String> getUserInfo(final String[] querys) {
        try {
            return Arrays.stream(querys)
                    .map(info -> info.split("=")[1])
                    .collect(Collectors.toList());
        } catch (PatternSyntaxException e) {
            logger.error(e.getMessage());
            throw new IllegalArgumentException("404에러");
        }
    }

    private User createUser(final List<String> userInfo) {
        try {
            // id, password, nickname 순서
            return new User(userInfo.get(0), userInfo.get(2), userInfo.get(1));
        } catch (IndexOutOfBoundsException e) {
            logger.error(e.getMessage());
            throw new IllegalArgumentException("404에러");
        }
    }
}
