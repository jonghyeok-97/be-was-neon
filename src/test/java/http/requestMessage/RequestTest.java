package http.requestMessage;

import webserver.model.UserInfo;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

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

    @Test
    @DisplayName("Get요청이 들어왔을 때, 유저정보를 획득하면 빈 값을 얻는다")
    void getRequest() {
        Optional<String> optId = getRequest.get(UserInfo.USER_ID);
        Optional<String> optPassword = getRequest.get(UserInfo.PASSWORD);
        Optional<String> optName = getRequest.get(UserInfo.NICKNAME);
        Optional<String> optNull = getRequest.get(null);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(optId).isEmpty();
            softAssertions.assertThat(optPassword).isEmpty();
            softAssertions.assertThat(optName).isEmpty();
            softAssertions.assertThat(optNull).isEmpty();
        });
    }

    @Test
    @DisplayName("POST요청이 들어왔을 때, 유저정보를 획득하면 값을 얻는다")
    void postRequest() {
        Optional<String> optId = postRequest.get(UserInfo.USER_ID);
        Optional<String> optPassword = postRequest.get(UserInfo.PASSWORD);
        Optional<String> optName = postRequest.get(UserInfo.NICKNAME);
        Optional<String> optNull = postRequest.get(null);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(optId.get()).isEqualTo("jonghyeok");
            softAssertions.assertThat(optPassword.get()).isEqualTo("123");
            softAssertions.assertThat(optName.get()).isEqualTo("Gromit");
            softAssertions.assertThat(optNull).isEmpty();
        });
    }


}