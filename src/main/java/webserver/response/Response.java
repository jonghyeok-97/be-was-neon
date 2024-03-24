package webserver.response;

import webserver.utils.CRLF;

public class Response {
    private final StartLine startLine;
    private final StringBuilder headers;
    private final byte[] body;

    private Response(final StartLine startLine, final StringBuilder headers, final byte[] body) {
        this.startLine = startLine;
        this.headers = headers;
        this.body = body;
    }

    static public class Builder {
        private StartLine startLine;
        private StringBuilder headers = new StringBuilder();
        private byte[] body;

        public Builder(final StartLine startLine) {
            this.startLine = startLine;
        }

        public Builder contentType(final String contentType) {
            headers.append(CRLF.addNewLine("Content-Type: " + contentType));
            return this;
        }

        public Builder contentLength(final byte[] body) {
            headers.append(CRLF.addNewLine("Content-Length: " + body.length));
            this.body = body;
            return this;
        }

        public Builder location(final String location) {
            headers.append(CRLF.addNewLine("Location: " + location));
            return this;
        }

        public Builder cookie(final String cookie) {
            headers.append(CRLF.addNewLine("Set-Cookie: " + cookie));
            return this;
        }

        public Response build() {
            return new Response(startLine, headers, body);
        }
    }

    public String getStartLine() {
        return CRLF.addNewLine(startLine.getValue());
    }

    public String getHeader() {
        return headers.toString();
    }

    public String getEmptyLine() {
        return CRLF.addNewLine("");
    }

    public byte[] getBody() {
        if (body == null) {
            return new byte[0];
        }
        return body;
    }
}
