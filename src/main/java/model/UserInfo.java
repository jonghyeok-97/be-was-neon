package model;

import java.util.stream.Stream;

public enum UserInfo {
    USER_ID("userId"),
    PASSWORD("password"),
    NICKNAME("name");

    private final String type;

    UserInfo(final String type) {
        this.type = type;
    }

    public static UserInfo match(final String _type) {
        return Stream.of(values())
                .filter(info -> info.type.equals(_type))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 형식"));
    }
}
