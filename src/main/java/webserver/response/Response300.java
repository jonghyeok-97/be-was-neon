package webserver.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static webserver.response.Response.addNewLine;

public class Response300 implements Response {
    private enum StatusLine {
        Found("HTTP/1.1 302 Found ");

        private final String line;

        StatusLine(final String _line) {
            line = _line;
        }
    }

    private final Logger logger = LoggerFactory.getLogger(Response300.class);
    private final String REDIRECT_PATH = "/index.html";

    // addNewLine은 Response란 인터페이스의 static 메서드
    @Override
    public String getHeader() {
        final StringBuilder result = new StringBuilder();
        result.append(addNewLine(StatusLine.Found.line))
                .append(addNewLine(addNewLine("Location: " + REDIRECT_PATH)));
        return result.toString();
    }

    @Override
    public byte[] getBody() {
        return new byte[0];
    }
}
