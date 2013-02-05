package com.prettybit.socnet;

import com.prettybit.framework.RequestContext;
import com.prettybit.framework.container.Container;
import com.prettybit.socnet.be.AccountService;
import com.prettybit.socnet.be.AccountServiceImpl;
import com.prettybit.socnet.be.NewsService;
import com.prettybit.socnet.be.NewsServiceImpl;
import com.prettybit.socnet.rest.annotation.NewAccountValueFactoryProvider;
import org.glassfish.hk2.api.PerLookup;
import org.glassfish.hk2.utilities.Binder;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ApplicationHandler;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spi.internal.ValueFactoryProvider;

import javax.inject.Inject;
import javax.inject.Singleton;

import static com.prettybit.framework.container.bind.Bindings.bind;
import static java.util.Arrays.asList;

/**
 * @author Pavel Mikhalchuk
 */
public class WebApp extends ResourceConfig {

    @Inject
    public WebApp(ApplicationHandler handler) {
        bind(AccountServiceImpl.class).to(AccountService.class).in(Singleton.class);
        bind(NewsServiceImpl.class).to(NewsService.class).in(Singleton.class);
        init(handler);
    }

    private void init(ApplicationHandler handler) {
        handler.registerAdditionalBinders(asList(binder()));
    }

    private static Binder binder() {
        return new AbstractBinder() {
            @Override
            protected void configure() {
                install(Container.binder());
                bindAsContract(RequestContext.class).in(PerLookup.class);
                bind(NewAccountValueFactoryProvider.class).to(ValueFactoryProvider.class).in(Singleton.class);
                bind(AccountServiceImpl.class).to(AccountService.class).in(PerLookup.class);
                bind(NewsServiceImpl.class).to(NewsService.class).in(Singleton.class);
            }
        };
    }

}