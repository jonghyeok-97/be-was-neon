package webserver;

public class Response {
    private final byte[] htmlFileDatas;

    public Response(final byte[] htmlFileDatas) {
        this.htmlFileDatas = htmlFileDatas;
    }

    public String get200Header() {
        final StringBuilder header = new StringBuilder();
        header.append(addNewLine("HTTP/1.1 200 OK "))
                .append(addNewLine("Content-Type: text/html;charset=utf-8"))
                .append(addNewLine(addNewLine("Content-Length: " + htmlFileDatas.length)));

        return header.toString();
    }

    private String addNewLine(final String line) {
        return line + System.lineSeparator();
    }

    public byte[] getBodyData() {
        return htmlFileDatas;
    }
}
