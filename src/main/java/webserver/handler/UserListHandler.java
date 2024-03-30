package webserver.handler;

import http.SessionManager;
import http.requestMessage.Request;
import http.requestMessage.RequestHeader;
import http.responseMessage.Response;
import http.responseMessage.StatusLine;
import webserver.db.Database;
import webserver.model.User;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserListHandler implements Handler {
    private final RequestHeader header;
    private final Request request;

    public UserListHandler(Request request) {
        this.header = request.getHeader();
        this.request = request;
    }

    @Override
    public boolean isExecute() {
        return request.isGet() && request.corresponds("/user/list");
    }

    @Override
    public Response handle() {
        // 1. 로그인했는지 확인 -> 들어온 세션이 있는지 확ㅇ;ㄴ
        // 2. 있으면 user목록 보여주기
        // 3. 없으면 로그인페이지로 리다이렉트
        Optional<String> optSessionID = header.getSessionID();

        // 서버에 세션있으면 sessionID 그대로 반환, 세션이 없으면 "" 반환
        final String sessionID = optSessionID.filter(SessionManager::hasSessionID).orElse("");
        if (sessionID.isEmpty()) {
            return new Response.Builder(StatusLine.Found_302)
                    .location("/login/index.html")
                    .build();
        }

        // 세션 있으면 name.txt란 파일에
        List<String> allUserNames = Database.findAllUser().stream().map(User::getName).collect(Collectors.toList());
        FileHandler fileHandler = new FileHandler("/list/name.txt");
        fileHandler.write(allUserNames);
        String subType = fileHandler.findSubTypeOfMIME();
        byte[] data = fileHandler.read();
        return new Response.Builder(StatusLine.OK_200)
                .contentType(subType)
                .contentLength(data)
                .build();
    }
}
