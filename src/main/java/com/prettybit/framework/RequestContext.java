package com.prettybit.framework;

import org.glassfish.jersey.server.ContainerRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Provider;

import static com.prettybit.framework.utils.Fn.SimpleFunction;

/**
 * @author Pavel Mikhalchuk
 */
public class RequestContext {

    private Logger log = LoggerFactory.getLogger(RequestContext.class);

    @Inject
    private Provider<ContainerRequest> request;

    public Object get(String name) {
        return request().getProperty(name);
    }

    public void set(String name, Object o) {
        request().setProperty(name, o);
    }

    @SuppressWarnings("unchecked")
    public <T> T getOrSet(String name, SimpleFunction<T> createObject) {
        if (get(name) == null) { set(name, createObject.apply()); }
        return (T) get(name);
    }

    public ContainerRequest request() {
        return request.get();
    }

}