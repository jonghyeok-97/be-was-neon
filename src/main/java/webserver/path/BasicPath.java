package webserver.path;

public enum BasicPath {
    HOME("/index.html");

    private final String path;

    BasicPath(final String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

}
