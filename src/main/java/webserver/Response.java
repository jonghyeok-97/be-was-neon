package webserver;

import java.util.Map;

public class Response {
    private final Map<String, String> imageMapper;
    private final byte[] htmlFileDatas;
    private final String extension;

    public Response(final byte[] htmlFileDatas, final String extension) {
        this.htmlFileDatas = htmlFileDatas;
        this.extension = extension;
        imageMapper = Map.ofEntries(
                Map.entry("html", "text/html;charset=utf-8"),
                Map.entry("css", "text/css"),
                Map.entry("svg", "image/svg+xml"),
                Map.entry("ico", "image/x-icon"),
                Map.entry("png", "image/png"),
                Map.entry("jpg", "image/jpeg"),
                Map.entry("jpeg", "image/jpeg"),
                Map.entry("js", "application/javascript")
        );
    }

    public String get200Header() {
        final String type = find(extension);
        final StringBuilder header = new StringBuilder();
        header.append(addNewLine("HTTP/1.1 200 OK "))
                .append(addNewLine("Content-Type: " + type))
                .append(addNewLine(addNewLine("Content-Length: " + htmlFileDatas.length)));

        return header.toString();
    }

    private String find(final String extensionType) {
        return imageMapper.keySet().stream()
                .filter(type -> type.equals(extensionType))
                .map(type -> imageMapper.get(type))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("404"));
    }

    private String addNewLine(final String line) {
        return line + System.lineSeparator();
    }

    public byte[] getBodyData() {
        return htmlFileDatas;
    }
}
