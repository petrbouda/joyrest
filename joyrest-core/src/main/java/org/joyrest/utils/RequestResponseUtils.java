package org.joyrest.utils;

import static java.util.function.Function.identity;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.joyrest.model.http.HeaderName;

public final class RequestResponseUtils {

	public static Map<HeaderName, String> createHeaders(Iterable<String> headerNames,
			                                            Function<String, String> headerValueFunction) {
		Function<String, String> headerMapper = name -> {
			String value = headerValueFunction.apply(name);
			if (value.contains(";")) {
				value = value.split(";")[0];
			}
			return value;
		};

		return StreamSupport.stream(headerNames.spliterator(), false)
			.collect(Collectors.toMap(HeaderName::of, headerMapper));
	}

	public static Map<String, String[]> createQueryParams(Iterable<String> paramNames,
                                                          Function<String, String[]> paramValueFunction) {
        return StreamSupport.stream(paramNames.spliterator(), false)
                .collect(Collectors.toMap(identity(), paramValueFunction::apply));
    }

    public static String createPath(String requestUri, String contextPath) {
        return requestUri.replaceFirst(contextPath, "");
    }

}
