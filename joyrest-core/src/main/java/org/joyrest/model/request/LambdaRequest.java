package org.joyrest.model.request;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toMap;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.StreamSupport;

import org.joyrest.exception.type.InvalidConfigurationException;
import org.joyrest.model.http.HeaderName;

public class LambdaRequest<E> extends InternalRequest<E> {

	private final Function<String, String> headerProvider;
	private final Function<String, String[]> queryParamProvider;

	private Iterable<String> headerNames = null;
	private Iterable<String> queryParamNames = null;

	public LambdaRequest(Function<String, String> headerProvider, Function<String, String[]> queryParamProvider) {
		requireNonNull(headerProvider, "Header provider cannot be null.");
		requireNonNull(queryParamProvider, "QueryParamProvider cannot be null.");

		this.headerProvider = headerProvider;
		this.queryParamProvider = queryParamProvider;
	}

	public void setHeaderNames(Iterable<String> headerNames) {
		requireNonNull(headerNames, "Collection of header names cannot be null.");
		this.headerNames = headerNames;
	}

	public void setQueryParamNames(Iterable<String> queryParamNames) {
		requireNonNull(queryParamNames, "Collection of query param names cannot be null.");
		this.queryParamNames = queryParamNames;
	}

	@Override
	public Map<HeaderName, String> getHeaders() {
		if (super.headers != null) {
			return super.getHeaders();
		}

        if (headerNames == null) {
            throw new InvalidConfigurationException("Header names cannot be null.");
        }

		super.headers = StreamSupport.stream(headerNames.spliterator(), false)
			.collect(toMap(HeaderName::of, name -> getHeader(HeaderName.of(name)).get()));
		return super.headers;
	}

	@Override
	public Map<String, String[]> getQueryParams() {
		if (super.queryParams != null) {
			return super.getQueryParams();
		}

        if (queryParamNames == null) {
            throw new InvalidConfigurationException("Query param names cannot be null.");
        }

		super.queryParams = StreamSupport.stream(queryParamNames.spliterator(), false)
			.collect(toMap(Function.identity(), name -> getQueryParams(name).get()));
		return super.queryParams;
	}

	@Override
	public Optional<String[]> getQueryParams(String name) {
		String[] values = queryParamProvider.apply(name);
		return Optional.ofNullable(values);
	}

	@Override
	public Optional<String> getHeader(HeaderName name) {
		String headerValue = headerProvider.apply(name.getValue());
		return Optional.ofNullable(headerValue);
	}

	@Override
	public void setQueryParams(Map<String, String[]> queryParams) {
		throw new UnsupportedOperationException(
				"Class LambdaRequest uses lambda expression for providing query params");
	}

	@Override
	public void setHeaders(Map<HeaderName, String> headers) {
		throw new UnsupportedOperationException(
				"Class LambdaRequest uses lambda expression for providing headerNames");
	}
}
