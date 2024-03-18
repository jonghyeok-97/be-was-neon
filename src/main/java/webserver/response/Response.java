package webserver.response;

import java.io.IOException;

public interface Response {

    String getHeader() throws IOException;
    byte[] getBody() throws IOException;
}
