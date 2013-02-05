package com.prettybit.framework.container.jpa;

import com.prettybit.framework.container.jersey.AbstractInjectionResolver;
import org.glassfish.hk2.api.Injectee;
import org.glassfish.hk2.api.ServiceHandle;

import javax.inject.Inject;
import javax.persistence.PersistenceContext;

/**
 * @author Pavel Mikhalchuk
 */
public class PersistenceContextInjector extends AbstractInjectionResolver<PersistenceContext> {

    @Inject
    private PersistenceContextProvider provider;

    public PersistenceContextInjector() {
        super(false, false);
    }

    @Override
    public Object resolve(Injectee injectee, ServiceHandle<?> root) {
        return provider.get();
    }

}