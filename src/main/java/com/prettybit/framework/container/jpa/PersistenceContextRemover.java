package com.prettybit.framework.container.jpa;

import org.glassfish.jersey.server.model.Invocable;
import org.glassfish.jersey.server.spi.internal.ResourceMethodInvocationHandlerProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author Pavel Mikhalchuk
 */
public class PersistenceContextRemover implements ResourceMethodInvocationHandlerProvider {

    private Logger log = LoggerFactory.getLogger(PersistenceContextRemover.class);

    @Inject
    private PersistenceContextProvider provider;

    @Override
    public InvocationHandler create(Invocable method) {
        return new InvocationHandler() {
            @Override
            public Object invoke(Object target, Method method, Object[] args) throws Throwable {
                try {
                    return method.invoke(target, args);
                } finally {
                    log.debug("Closing Entity Manager [" + provider.get() + "]");
                    provider.close();
                }
            }
        };
    }

}