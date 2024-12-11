package util;

@FunctionalInterface
public interface Parser<T> {
    T parse(String value);
}
