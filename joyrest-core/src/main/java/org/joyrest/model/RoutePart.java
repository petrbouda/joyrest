package org.joyrest.model;

import org.joyrest.extractor.param.PathType;

public class RoutePart<T> {

    public static enum Type {
        PATH, PARAM
    }

    private final Type type;

    private final PathType<T> pathType;

    private final String value;

    public RoutePart(Type type, String value, PathType<T> pathType) {
        this.type = type;
        this.value = value;
        this.pathType = pathType;
    }

    public Type getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public PathType<T> getPathType() {
        return pathType;
    }
}
