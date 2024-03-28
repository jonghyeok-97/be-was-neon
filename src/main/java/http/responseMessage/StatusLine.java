package http.responseMessage;

public enum StatusLine {
    OK_200("HTTP/1.1 200 OK "),
    Found_302("HTTP/1.1 302 Found ");

    private final String line;

    StatusLine(final String _line) {
        line = _line;
    }

    public String getValue() {
        return line;
    }
}