package webserver.request;

import webserver.request.body.RequestBody;
import webserver.request.line.RequestLine;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static webserver.HttpStandard.*;

public class RequestFactory {
    private final List<String> requestMessages;

    public RequestFactory(final String requestMessage) {
        requestMessages = Stream.of(requestMessage.split(CARRIAGE_LINE_FEED.getValue()))
                .collect(Collectors.toList());
    }

    public RequestLine createRequestLine() {
        final String requestLine = requestMessages.get(0);
        return new RequestLine(requestLine);
    }

    public Optional<RequestBody> createRequestBody(final RequestLine requestLine) {
        if (requestLine.isPOST()) {
            final String body = requestMessages.get(requestMessages.size() - 1);
            return Optional.of(new RequestBody(body));
        }
        return Optional.empty();
    }
}
