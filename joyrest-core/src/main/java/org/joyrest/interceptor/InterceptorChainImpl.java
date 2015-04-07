package org.joyrest.interceptor;

import org.joyrest.model.request.JoyRequest;
import org.joyrest.model.response.DefaultJoyResponse;
import org.joyrest.routing.Route;

import java.util.ArrayDeque;
import java.util.Queue;

import static java.util.Objects.requireNonNull;

public class InterceptorChainImpl implements InterceptorChain {

    private final Queue<Interceptor> interceptors = new ArrayDeque<>();

    private final Route route;

    public InterceptorChainImpl(Route route) {
        requireNonNull(route, "Route cannot be null in InterceptorChain.");
        this.route = route;
        this.interceptors.addAll(route.getInterceptors());
    }

    @Override
    public DefaultJoyResponse proceed(JoyRequest request) {
        requireNonNull(request, "JoyRequest cannot be null.");
        Interceptor interceptor = interceptors.poll();

        if (interceptor != null) {
            return interceptor.intercept(this, request);
        }
        return route.execute(request);
    }
}