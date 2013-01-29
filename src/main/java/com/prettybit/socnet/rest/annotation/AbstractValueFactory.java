package com.prettybit.socnet.rest.annotation;

import org.glassfish.hk2.api.Factory;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Pavel Mikhalchuk
 */
public abstract class AbstractValueFactory<T> implements Factory<T> {

    @Inject
    private Provider<HttpServletRequest> request;

    @Override
    public void dispose(T instance) {
    }

    protected HttpServletRequest request() {
        return request.get();
    }

}