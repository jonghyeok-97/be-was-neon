package webserver.handler;

import http.requestMessage.RequestBody;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class UserInfo {
    private final Map<String, String> userInfos;

    UserInfo(final RequestBody body) {
        final String bodyLine = body.getBody();
        String[] querys = getQuerys(bodyLine);
        this.userInfos = getUserInfo(querys);
    }

    private String[] getQuerys(final String bodyLine) {
        return bodyLine.split("&");
    }

    private Map<String, String> getUserInfo(final String[] querys) {
        final Map<String, String> _userInfo = new HashMap<>();
        Arrays.stream(querys)
                .forEach(query -> {
                    final String[] userInfo = query.split("=");
                    final String type = userInfo[0];
                    final String value = userInfo[1];
                    _userInfo.put(type, value);
                });
        return _userInfo;
    }

    String get(String type) {
        return userInfos.get(type);
    }
}

