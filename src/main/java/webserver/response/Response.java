package webserver.response;

import java.io.IOException;

import static webserver.HttpStandard.CARRIAGE_LINE_FEED;

public interface Response {

    String getHeader() throws IOException;
    byte[] getBody() throws IOException;

    static String addNewLine(final String line) {
        return line + CARRIAGE_LINE_FEED.getValue();
    }
}
