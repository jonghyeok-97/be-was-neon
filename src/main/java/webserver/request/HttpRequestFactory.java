package webserver.request;

import webserver.request.body.HttpRequestBody;
import webserver.request.line.HttpRequestLine;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HttpRequestFactory {
    private final List<String> requestMessages;

    public HttpRequestFactory(final String requestMessage) {
        requestMessages = Stream.of(requestMessage.split("\r\n"))
                .collect(Collectors.toList());
    }

    public HttpRequestLine createRequestLine() {
        final String requestLine = requestMessages.get(0);
        return new HttpRequestLine(requestLine);
    }

    public Optional<HttpRequestBody> createRequestBody(final HttpRequestLine requestLine) {
        if (requestLine.isPOST()) {
            final String body = requestMessages.get(requestMessages.size() - 1);
            return Optional.of(new HttpRequestBody(body));
        }
        return Optional.empty();
    }
}
