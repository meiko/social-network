package com.prettybit.socnet.container;

import org.glassfish.jersey.server.spi.AbstractContainerLifecycleListener;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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

}