package webserver;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebServer {
    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);
    private static final int DEFAULT_PORT = 8080;

    public static void main(String args[]) throws Exception {
        int port = 0;
        if (args == null || args.length == 0) {
            port = DEFAULT_PORT;
        } else {
            port = Integer.parseInt(args[0]);
        }

        final ExecutorService executorService = Executors.newCachedThreadPool();

        // 서버소켓을 생성한다. 웹서버는 기본적으로 8080번 포트를 사용한다.
        try (ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);

            // 클라이언트가 연결될때까지 대기한다.
            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
                CompletableFuture.runAsync(new RequestHandler(connection), executorService);
            }

        } finally {
            // 스레드 풀의 워커 스레드가 만들어진 다음 다른 태스크 제출을 기다리면서 종료되지 않은 상태처럼
            // 스레드 풀에서 스레드를 관리 잘못할 경우를 없애서 프로그램 종료.
            executorService.shutdown();
        }
    }
}
