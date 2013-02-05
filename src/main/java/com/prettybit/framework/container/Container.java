package com.prettybit.framework.container;

import com.prettybit.framework.container.bind.Bindings;
import com.prettybit.framework.container.bind.Injector;
import com.prettybit.framework.container.jpa.PersistenceContextInjector;
import com.prettybit.framework.container.jpa.PersistenceContextProvider;
import com.prettybit.framework.container.jpa.PersistenceContextRemover;
import org.glassfish.hk2.api.InjectionResolver;
import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.spi.AbstractContainerLifecycleListener;
import org.glassfish.jersey.server.spi.ContainerLifecycleListener;
import org.glassfish.jersey.server.spi.internal.ResourceMethodInvocationHandlerProvider;

import javax.inject.Inject;
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
    public void onShutdown(org.glassfish.jersey.server.spi.Container container) { factory.close(); }

    public static EntityManager newEntityManager() { return factory.createEntityManager(); }

    public static Binder binder() { return new Binder(); }

    public static class Binder extends AbstractBinder {
        @Override
        protected void configure() {
            bind(Container.class).to(ContainerLifecycleListener.class);
            bindAsContract(PersistenceContextProvider.class).in(Singleton.class);
            bind(PersistenceContextInjector.class).to(new TypeLiteral<InjectionResolver<PersistenceContext>>() {}).in(Singleton.class);
            bind(PersistenceContextRemover.class).to(ResourceMethodInvocationHandlerProvider.class).in(Singleton.class);
            bind(Injector.class).to(new TypeLiteral<InjectionResolver<Inject>>() {}).in(Singleton.class).ranked(1);
            Bindings.lock();
        }
    }

}