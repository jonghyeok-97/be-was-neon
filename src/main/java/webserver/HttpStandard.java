package webserver;

public enum HttpStandard {
    CARRIAGE_LINE_FEED("\r\n");

    private final String value;

    HttpStandard(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
