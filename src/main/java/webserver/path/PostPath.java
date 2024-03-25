package webserver.path;

import java.util.stream.Stream;

public enum PostPath {

    LOGIN("/login"),
    REGISTER("/create");

    private final String path;

    PostPath(final String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public static boolean has(final String uri) {
        return Stream.of(values())
                .anyMatch(postPath -> postPath.path.equals(uri));
    }

}
