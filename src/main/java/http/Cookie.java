package http;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.joining;

public class Cookie {
    private final List<Property> properties;

    public Cookie(List<Property> _properties) {
        this.properties = new ArrayList<>(_properties);

    }

    public String getProperties() {
        return properties.stream()
                .map(Property::getMessage)
                .collect(joining(" "));
    }
}
