package com.prettybit.framework;

import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.server.model.Parameter;
import org.glassfish.jersey.server.spi.internal.ValueFactoryProvider;

import javax.inject.Inject;
import java.lang.annotation.Annotation;

/**
 * @author Pavel Mikhalchuk
 */
public abstract class AbstractValueFactoryProvider implements ValueFactoryProvider {

    @Inject
    private ServiceLocator locator;

    private Class<? extends Annotation> annotation;

    protected AbstractValueFactoryProvider(Class<? extends Annotation> annotation) {
        this.annotation = annotation;
    }

    @Override
    public Factory<?> getValueFactory(Parameter p) {
        if (!checkAnnotation(p)) return null;
        Factory<?> factory = createFactory();
        locator.inject(factory);
        return factory;
    }

    @Override
    public PriorityType getPriority() { return Priority.NORMAL; }

    protected abstract Factory<?> createFactory();

    private boolean checkAnnotation(Parameter p) { return p.getAnnotation().annotationType().isAssignableFrom(annotation); }

}