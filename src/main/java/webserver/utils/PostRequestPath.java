package webserver.utils;

public enum PostRequestPath {
    LOGIN("/login"),
    REGISTER("/create");

    private final String path;

    PostRequestPath(final String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
