package webserver;

import java.io.IOException;

public class Response300 implements Response{
    private static final String REDIRECT_PATH = "src/main/resources/static/index.html";

    @Override
    public String getHeader() throws IOException{
        final StringBuilder result = new StringBuilder();
        result.append(addNewLine("HTTP/1.1 302 Found "))
                .append(addNewLine("Location: " + REDIRECT_PATH));
        return result.toString();
    }

    @Override
    public byte[] getBody() throws IOException {
        return new byte[0];
    }

    private String addNewLine(final String line) {
        return line + "\r\n";
    }
}
