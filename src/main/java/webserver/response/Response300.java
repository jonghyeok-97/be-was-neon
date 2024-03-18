package webserver.response;

import static webserver.response.Response.*;

public class Response300 implements Response {

    private enum StatusLine {
        Found("HTTP/1.1 302 Found ");

        private final String line;

        StatusLine(final String _line) {
            line = _line;
        }
    }

    private final String REDIRECT_PATH = "src/main/resources/static/index.html";

    @Override
    public String getHeader() {
        final StringBuilder result = new StringBuilder();
        result.append(addNewLine(StatusLine.Found.line))
                .append(addNewLine("Location: " + REDIRECT_PATH));
        return result.toString();
    }

    @Override
    public byte[] getBody() {
        return new byte[0];
    }
}
