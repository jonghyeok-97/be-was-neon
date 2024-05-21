package webserver.handler.authentication;

import http.SessionDB;
import http.responseMessage.Response;
import http.responseMessage.StatusLine;
import webserver.db.Database;
import webserver.model.User;

import static java.util.stream.Collectors.joining;

public class UserList implements UserAuthentication {
    private final String sessionID;

    public UserList(String sessionID) {
        this.sessionID = sessionID;
    }

    @Override
    public boolean isPass() {
        return SessionDB.hasSessionID(sessionID);
    }

    @Override
    public void handleSessionDB() {

    }

    @Override
    public Response getSuccessResponse() {
        final String allUserNames = Database.findAllUser().stream()
                .map(User::getName)
                .collect(joining("\n"));

        byte[] data = allUserNames.getBytes();
        String subType = "text/plain";

        return new Response.Builder(StatusLine.OK_200)
                .contentType(subType)
                .contentLength(data)
                .build();
    }

    @Override
    public Response getFailedResponse() {
        return new Response.Builder(StatusLine.Found_302)
                .location("/login/index.html")
                .build();
    }
}
