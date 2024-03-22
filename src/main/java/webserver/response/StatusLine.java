package webserver.response;

public enum StatusLine {
    OK("HTTP/1.1 200 OK "),
    Found("HTTP/1.1 302 Found ");

    private final String line;

    StatusLine(final String _line) {
        line = _line;
    }

    public String getValue() {
        return line;
    }
}