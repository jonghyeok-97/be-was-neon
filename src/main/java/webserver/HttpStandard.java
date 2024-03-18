package webserver;

public enum HttpStandard {
    CARRIAGE_LINE_FEED("\r\n");

    private final String carriageAndLineFeed;

    HttpStandard(final String _carriageAndLineFeed) {
        carriageAndLineFeed = _carriageAndLineFeed;
    }

    public String getValue() {
        return carriageAndLineFeed;
    }
}
