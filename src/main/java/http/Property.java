package http;

import java.util.Objects;

public class Property {
    private final String key;
    private final String value;

    public Property(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getMessage() {
        return key + "=" + value + ";";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Property property = (Property) o;
        return Objects.equals(key, property.key) && Objects.equals(value, property.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }
}
