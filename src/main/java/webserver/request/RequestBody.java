package webserver.request;

import model.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.regex.PatternSyntaxException;

public class RequestBody {
    private static final Logger logger = LoggerFactory.getLogger(RequestBody.class);

    // USER_ID : "jonghyeok"
    // PASSWORD : 123
    // NAME : "Gromit"
    private final Map<UserInfo, String> userInfoMap;

    RequestBody(final String body) {
        try {
            final String[] querys = getQuerys(body);
            userInfoMap = getUserInfo(querys);
        } catch (PatternSyntaxException e) {
            logger.error(e.getMessage());
            throw new IllegalArgumentException("404에러");
        }
    }

    Optional<String> findValueFrom(final UserInfo userInfo) {
        return Optional.ofNullable(userInfo)
                .map(userInfoMap::get);
    }

    private String[] getQuerys(final String queryParmas) {
        return queryParmas.split("&");
    }

    private Map<UserInfo, String> getUserInfo(final String[] querys) {
        final Map<UserInfo, String> _userInfo = new HashMap<>();
        Arrays.stream(querys)
                .forEach(query -> {
                    final String[] userInfo = query.split("=");
                    final String type = userInfo[0];
                    final String value = userInfo[1];
                    logger.debug("타입 : 값 -> {} : {}", type, value);
                    _userInfo.put(UserInfo.match(type), value);
                });
        return _userInfo;
    }
}
