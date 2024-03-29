package webserver.handler;

import http.CRLF;
import http.requestMessage.RequestHeader;
import http.responseMessage.Response;
import http.responseMessage.StatusLine;
import webserver.db.Database;
import webserver.db.SessionManager;
import webserver.model.User;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        List<String> allUserNames = Database.findAllUser().stream().map(User::getName).collect(Collectors.toList());
        FileHandler fileHandler = new FileHandler("/list/name.txt");
        final StringBuilder sb = new StringBuilder();
        for (String allUserName : allUserNames) {
            sb.append(CRLF.addNewLine(allUserName));
        }

        String subType = fileHandler.findSubTypeOfMIME();
        byte[] data = fileHandler.read();
        return new Response.Builder(StatusLine.OK_200)
                .contentType(subType)
                .contentLength(data)
                .build();
    }
}
