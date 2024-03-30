package webserver.handler;

import java.util.List;

public class Handlers {
    private final List<Handler> handlers;

    public Handlers(final List<Handler> handlers) {
        this.handlers = handlers;
    }

    public Handler find() {
        return handlers.stream()
                .filter(Handler::isExecute)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("404에러"));
    }
}
