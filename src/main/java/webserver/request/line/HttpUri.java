package webserver.request.line;

import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;

public class HttpUri {
    private final Logger logger = LoggerFactory.getLogger(HttpUri.class);
    static final String RESOURCES_BASE_PATH = "src/main/resources/static";
    private final String uri;

    public HttpUri(final String uri) {
        logger.debug("URI : {}", uri);
        this.uri = uri;
        validateExtension(uri);
    }

    public File respond() {
        if (uri.contains("create")) {
            addUserToDB();
            return new File(RESOURCES_BASE_PATH + "/index.html");
        }
        return new File(RESOURCES_BASE_PATH + uri);
    }

    private void addUserToDB() {
        final String queryParams = getQueryParameters(uri);
        final String[] querys = getQuerys(queryParams);
        final List<String> userInfo = getUserInfo(querys);
        final User user = createUser(userInfo);
        logger.debug("저장되는 User : {}", user);
        Database.addUser(user);
    }

    private String getQueryParameters(final String uri) {
        return uri.split("\\?")[1];
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

    private void validateExtension(final String uri) {
        if (!uri.contains(".")) {
            throw new IllegalArgumentException("404에러");
        }
    }
}
