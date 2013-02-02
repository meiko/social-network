package com.prettybit.framework.container;

import org.glassfish.hk2.api.Injectee;
import org.glassfish.hk2.api.InjectionResolver;
import org.glassfish.hk2.api.ServiceHandle;

import javax.inject.Inject;
import javax.persistence.PersistenceContext;

/**
 * @author Pavel Mikhalchuk
 */
public class PersistenceContextInjector implements InjectionResolver<PersistenceContext> {

    @Inject
    private PersistenceContextProvider provider;

    @Override
    public Object resolve(Injectee injectee, ServiceHandle<?> root) {
        return provider.get();
    }

    @Override
    public boolean isConstructorParameterIndicator() {
        return false;
    }

    @Override
    public boolean isMethodParameterIndicator() {
        return false;
    }

}