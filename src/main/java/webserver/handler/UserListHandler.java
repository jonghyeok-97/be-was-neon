package webserver.handler;

import webserver.db.SessionManager;
import http.requestMessage.RequestHeader;
import http.responseMessage.Response;
import http.responseMessage.StatusLine;
import webserver.db.Database;

import java.util.List;
import java.util.Optional;

public class UserListHandler implements Handler {
    private final RequestHeader header;

    UserListHandler(RequestHeader header) {
        this.header = header;
    }

    @Override
    public Response handle() {
        Optional<String> optSessionID = header.getSessionID();

        // 서버에 세션있으면 sessionID 그대로 반환, 세션이 없으면 "" 반환
        final String sessionID = optSessionID.filter(SessionManager::hasSessionID).orElse("");
        if (sessionID.isEmpty()) {
            return new Response.Builder(StatusLine.Found_302)
                    .location("/login/index.html")
                    .build();
        }

        // 세션 있으면 name.txt란 파일에
        List<String> allUserNames = Database.findAllUserName();
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
