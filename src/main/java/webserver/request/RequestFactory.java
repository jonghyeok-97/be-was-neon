package webserver.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.request.body.RequestBody;
import webserver.request.line.RequestLine;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static webserver.HttpStandard.*;

public class RequestFactory {
    private final Logger logger = LoggerFactory.getLogger(RequestFactory.class);
    private final List<String> requestMessages;

    public RequestFactory(final String requestMessage) {
        requestMessages = Stream.of(requestMessage.split(CARRIAGE_LINE_FEED.getValue()))
                .collect(Collectors.toList());
    }

    public RequestLine createRequestLine() {
        final String requestLine = getLine(0);
        return new RequestLine(requestLine);
    }

    public Optional<RequestBody> createOptRequestBody(final RequestLine requestLine) {
        if (requestLine.isPOST()) {
            final String body = getLine(requestMessages.size() - 1);
            return Optional.of(new RequestBody(body));
        }
        return Optional.empty();
    }

    private String getLine(final int position) {
        try {
            return requestMessages.get(position);
        } catch (IndexOutOfBoundsException e) {
            logger.error(e.getMessage());
            throw new IllegalArgumentException("404에러");
        }
    }
}
