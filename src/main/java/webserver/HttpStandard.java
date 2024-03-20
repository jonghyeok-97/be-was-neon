package webserver;

public enum HttpStandard {
    CRLF("\r\n");

    private final String value;

    HttpStandard(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static String addNewLine(final String line) {
        return line + CRLF.value;
    }
}
