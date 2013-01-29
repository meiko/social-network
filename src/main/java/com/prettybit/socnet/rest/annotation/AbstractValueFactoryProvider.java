package com.prettybit.socnet.rest.annotation;

import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.server.model.Parameter;
import org.glassfish.jersey.server.spi.internal.ValueFactoryProvider;

import javax.inject.Inject;

/**
 * @author Pavel Mikhalchuk
 */
public abstract class AbstractValueFactoryProvider implements ValueFactoryProvider {

    @Inject
    private ServiceLocator locator;

    @Override
    public Factory<?> getValueFactory(Parameter parameter) {
        Factory<?> factory = createFactory();
        locator.inject(factory);
        return factory;
    }

    @Override
    public PriorityType getPriority() {
        return Priority.NORMAL;
    }

    protected abstract Factory<?> createFactory();

}