package webserver.response;

import java.util.stream.Stream;

// https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/MIME_types
public enum MIME {
    HTML(".html", "text/html"),
    CSS(".css", "text/css"),
    SVG(".svg", "image/svg+xml"),
    ICO(".ico", "image/x-icon"),
    PNG(".png", "image/png"),
    JPG(".jpg", "image/jpg"),
    JPEG(".jpeg", "image/jpeg"),
    NONE("", "");

    private final String type;
    private final String subType;

    MIME(final String type, final String subType) {
        this.type = type;
        this.subType = subType;
    }

    public static MIME find(final String type) {
        return Stream.of(values())
                .filter(mime -> mime.type.equals(type))
                .findFirst()
                .orElse(NONE);
    }

    public String getSubType() {
        return subType;
    }
}
