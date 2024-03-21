package webserver.utils;

public class CRLF {
    private static final String CRLF = "\r\n";

    public static String addNewLine(final String line) {
        return line + CRLF;
    }

    public static String[] split(final String line) {
        return line.split(CRLF);
    }
}
