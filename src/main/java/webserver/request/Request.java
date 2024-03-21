package webserver.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.response.Response;
import webserver.response.Response200;
import webserver.response.Response300;
import webserver.utils.CRLF;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Request {
    private static final Logger logger = LoggerFactory.getLogger(Request.class);

    private final RequestLine line;
    private final RequestHeader headers;
    private final Optional<RequestBody> optBody;

    // inner class 사용해서 파싱
    // inner class는 Request를 만들 때만 사용하기 때문에 private 지정
    public Request(final String requestMessage) {
        logger.debug("들어온 메시지 : {}", requestMessage);
        try {
            final RequestFactory factory = new RequestFactory(requestMessage);
            line = factory.createRequestLine();
            headers = factory.createHeader();
            optBody = factory.createOptRequestBody();
        } catch (IndexOutOfBoundsException e) {
            logger.error(e.getMessage());
            throw new IllegalArgumentException("404에러");
        }
    }

    private class RequestFactory {
        final List<String> messages;

        RequestFactory(final String requestMessage) {
            messages = Stream.of(CRLF.split(requestMessage))
                    .collect(Collectors.toList());
        }

        RequestLine createRequestLine() {
            final String requestLine = messages.get(0);
            return new RequestLine(requestLine);
        }

        RequestHeader createHeader() {
            final int startHeaderPosition = 1;
            final int lastHeaderPosition;
            if (line.isPOST()) {
                lastHeaderPosition = messages.size() - 2;
                return new RequestHeader(messages.subList(startHeaderPosition, lastHeaderPosition));
            }
            lastHeaderPosition = messages.size();
            return new RequestHeader(messages.subList(startHeaderPosition, lastHeaderPosition));
        }

        Optional<RequestBody> createOptRequestBody() {
            if (line.isPOST()) {
                final int bodyPosition = messages.size() - 1;
                final String body = messages.get(bodyPosition);
                return Optional.of(new RequestBody(body));
            }
            return Optional.empty();
        }
    }

    public Response respond() throws IOException {
        optBody.ifPresent(RequestBody::addUserToDB);
        final Optional<File> optFile = line.findFile();
        if (optFile.isPresent()) {
            return new Response200(optFile.get());
        }
        return new Response300();
    }
}
