package webserver.response;

import webserver.utils.CRLF;

public class Response {
    private final StatusLine statusLine;
    private final StringBuilder headers;
    private final byte[] body;

    private Response(final StatusLine statusLine, final StringBuilder headers, final byte[] body) {
        this.statusLine = statusLine;
        this.headers = headers;
        this.body = body;
    }

    static public class Builder {
        private StatusLine statusLine;
        private StringBuilder headers = new StringBuilder();
        private byte[] body;

        public Builder(final StatusLine statusLine) {
            this.statusLine = statusLine;
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
            return new Response(statusLine, headers, body);
        }
    }

    public String getStatusLine() {
        return CRLF.addNewLine(statusLine.getValue());
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
