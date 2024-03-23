package webserver.path;

public enum FilePath {
    BASE("src/main/resources/static"),
    HOME("/index.html");

    private final String path;

    FilePath(final String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

}
