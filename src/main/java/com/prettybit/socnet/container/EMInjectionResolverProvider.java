package com.prettybit.socnet.container;

import org.glassfish.hk2.api.Injectee;
import org.glassfish.hk2.api.InjectionResolver;
import org.glassfish.hk2.api.ServiceHandle;

import javax.persistence.PersistenceContext;

/**
 * @author Pavel Mikhalchuk
 */
public class EMInjectionResolverProvider implements InjectionResolver<PersistenceContext> {

    @Override
    public Object resolve(Injectee injectee, ServiceHandle<?> root) {
        return Container.newEntityManager();
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