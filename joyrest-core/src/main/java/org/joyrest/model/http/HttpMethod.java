package org.joyrest.model.http;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public enum HttpMethod {

    OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT, PATCH;

    private static final Map<String, HttpMethod> methods;

    static {
        methods = Arrays.asList(values()).stream()
                .collect(Collectors.toMap(HttpMethod::name, Function.identity()));
    }

    public static final HttpMethod of(String methodName) {
        requireNonNull(methodName, "Method name cannot be null.");
        return methods.get(methodName.toUpperCase());
    }

}