package webserver.file;

import java.io.BufferedReader;
import java.io.IOException;

public class DynamicFile extends AbstractFile {

    private final String userName;

    public DynamicFile(String uri, String userName) {
        super(uri);
        this.userName = userName;
    }

    @Override
    protected StringBuilder readFile(BufferedReader br) throws IOException{
        final StringBuilder html = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            if (line.contains("account")) {
                html.append(line.replace("account", userName));
                continue;
            }
            html.append(line);
        }
        return html;
    }
}
