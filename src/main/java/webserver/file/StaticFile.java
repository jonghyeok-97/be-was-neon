package webserver.file;

import java.io.BufferedReader;
import java.io.IOException;

public class StaticFile extends AbstractFile{

    public StaticFile(String uri) {
        super(uri);
    }

    @Override
    protected StringBuilder readFile(BufferedReader br) throws IOException{
        final StringBuilder html = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            html.append(line);
        }
        return html;
    }
}
