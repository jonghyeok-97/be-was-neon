package webserver.response;

public enum StartLine {
    OK_200("HTTP/1.1 200 OK "),
    Found_302("HTTP/1.1 302 Found ");

    private final String line;

    StartLine(final String _line) {
        line = _line;
    }

    public String getValue() {
        return line;
    }
}