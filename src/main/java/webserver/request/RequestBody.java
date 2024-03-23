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
        final String[] querys = getQuerys(body);
        userInfoMap = get(querys);
    }

    // Optional.ofNullable vs Objects.requireNotNull -> Optional을 반환하고 싶을 때, Objects -> 매개변수가 null인지 확인할때.
    Optional<String> get(final UserInfo info) {
        return Optional.ofNullable(info)
                .map(userInfoMap::get);
    }

    private String[] getQuerys(final String queryParmas) {
        return queryParmas.split("&");
    }

    private Map<UserInfo, String> get(final String[] querys) {
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
