package http.requestMessage;

import org.junit.jupiter.api.BeforeEach;

class RequestTest {
    private Request getRequest;
    private Request postRequest;

    @BeforeEach
    void setUp() {
        getRequest = new Request(
                "GET /index.html HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Connection: keep-alive\r\n" +
                "Accept-Encoding: gzip, deflate, br, zstd\r\n" +
                "Accept-Language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7");

        postRequest = new Request(
                "POST /create HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Connection: keep-alive\r\n" +
                "Content-Length: 50\r\n" +
                "Cache-Control: max-age=0\r\n" +
                "Content-Type: application/x-www-form-urlencoded\r\n" +
                "\r\n" +
                // userId : 종혁, name : Gromit, password : 123
                "userId=jonghyeok&name=Gromit&password=123");
    }
}