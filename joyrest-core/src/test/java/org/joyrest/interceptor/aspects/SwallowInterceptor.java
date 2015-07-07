package org.joyrest.interceptor.aspects;

import java.util.Map;

import org.joyrest.interceptor.Interceptor;
import org.joyrest.interceptor.InterceptorChain;
import org.joyrest.model.http.HeaderName;
import org.joyrest.model.request.InternalRequest;
import org.joyrest.model.response.InternalResponse;

public class SwallowInterceptor implements Interceptor {

    @Override
    public InternalResponse<Object> around(InterceptorChain chain,
                                           InternalRequest<Object> request,
                                           InternalResponse<Object> response) {
        Map<HeaderName, String> headers = request.getHeaders();

        headers.put(HeaderName.of("Swallowed"), "YES");

        return response;
    }

    @Override
    public int getOrder() {
        return -50;
    }
}
