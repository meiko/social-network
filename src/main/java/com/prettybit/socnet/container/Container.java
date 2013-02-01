package com.prettybit.socnet.container;

import org.glassfish.hk2.api.InjectionResolver;
import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.spi.AbstractContainerLifecycleListener;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

/**
 * @author Pavel Mikhalchuk
 */
public class Container extends AbstractContainerLifecycleListener {

    private static EntityManagerFactory factory;

    @Override
    public void onStartup(org.glassfish.jersey.server.spi.Container container) {
        factory = Persistence.createEntityManagerFactory("soc-net");
    }

    @Override
    public void onShutdown(org.glassfish.jersey.server.spi.Container container) {
        factory.close();
    }

    public static EntityManager newEntityManager() {
        return factory.createEntityManager();
    }

    public static class Binder extends AbstractBinder {
        @Override
        protected void configure() {
            bind(EMInjectionResolverProvider.class).to(new TypeLiteral<InjectionResolver<PersistenceContext>>() {}).in(Singleton.class);
        }
    }

}