package webserver.request;

import model.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.handler.PostHandler;
import webserver.path.PostPath;
import webserver.utils.CRLF;

import java.util.*;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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
        } catch (IndexOutOfBoundsException | PatternSyntaxException e) {
            logger.error(e.getMessage());
            throw new IllegalArgumentException("404에러");
        }
    }

    // inner class로 만든 메소들 테스트 어떻게..?
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
            Map<String, String> headers = new HashMap<>();
            messages.stream()
                    .skip(1) // 스타터라인 제외
                    .filter(message -> message.contains(":")) // 헤더는 : 를 포함한다고 가정
                    .forEach(message -> {
                        final String[] keyValue = message.split(":");
                        final String headerKey = keyValue[0].trim(); // OWS 제거
                        final String headerValue = keyValue[1].trim();
                        headers.put(headerKey, headerValue);
                    });
            return new RequestHeader(headers);
        }

        Optional<RequestBody> createOptRequestBody() {
            Optional<Integer> contentLength = headers.getContentLength();
            return contentLength.filter(length -> length != 0)
                    .map(length -> {
                        final int bodyPosition = messages.size() - 1;
                        final String body = messages.get(bodyPosition);
                        return new RequestBody(body);
                    });
            }
        }

    public boolean isGet() {
        return line.isGet();
    }

    public RequestHeader getHeaders() {
        return headers;
    }

    // line 이 GET이면 URI를 획득, POST면 URI를 획득하지 못함.
    public Optional<String> getUri() {
        return line.getUri();
    }

    public Optional<String> get(final UserInfo userInfo) {
        // ofNullable 구문  :  userInfo 가 null 이 아니면 flatMap 실행
        // flatMap 구문   :   optBody가 null 이 아니면 body.findValueBy(info) 실행
        return Optional.ofNullable(userInfo)
                .flatMap(info ->
                        optBody.flatMap(body -> body.get(info))
                );
    }

    public boolean has(final PostPath path) {
        Objects.requireNonNull(path, path + "는 null이 될 수 없습니다.");
        return line.has(path);
    }
}

