package webserver.response;

import webserver.utils.CRLF;

public class Response {
    private final StringBuilder response;
    private final byte[] data;

    private Response(StringBuilder response, byte[] data) {
        this.response = response;
        this.data = data;
    }

    static public class Builder {
        private StringBuilder response = new StringBuilder();
        private byte[] data;

        public Builder(final String statusLine) {
            response.append(CRLF.addNewLine(statusLine));
        }

        public Builder contentType(final String contentType) {
            response.append(CRLF.addNewLine("Content-Type: " + contentType));
            return this;
        }

        public Builder contentLength(final byte[] data) {
            response.append(CRLF.addNewLine("Content-Length: " + data.length));
            this.data = data;
            return this;
        }

        public Builder location(final String location) {
            response.append(CRLF.addNewLine("Location: " + location));
            return this;
        }

        public Builder cookie(final String cookie) {
            response.append(CRLF.addNewLine("Set-Cookie: " + cookie));
            return this;
        }

        public Response build() {
            return new Response(response, data);
        }
    }

    public String getHeader() {
        return CRLF.addNewLine(response.toString());
    }

    public byte[] getBody() {
        if (data == null) {
            return new byte[0];
        }
        return data;
    }
}
