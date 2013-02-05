package com.prettybit.framework.container.bind;

import com.prettybit.framework.container.Container;
import com.prettybit.framework.container.jersey.AbstractInjectionResolver;
import com.prettybit.framework.container.jpa.TransactionManager;
import org.glassfish.hk2.api.Injectee;
import org.glassfish.hk2.api.InjectionResolver;
import org.glassfish.hk2.api.ServiceHandle;
import org.glassfish.hk2.api.ServiceLocator;

import javax.inject.Inject;
import javax.inject.Named;
import java.lang.reflect.Proxy;

/**
 * @author Pavel Mikhalchuk
 */
public class Injector extends AbstractInjectionResolver<Inject> {

    @Inject
    private ServiceLocator locator;

    @Inject
    @Named(InjectionResolver.SYSTEM_RESOLVER_NAME)
    private InjectionResolver<Inject> defaultResolver;

    public Injector() {
        super(false, false);
    }

    @Override
    public Object resolve(Injectee i, ServiceHandle<?> r) {
        return Bindings.has(i) ? resolve(i) : defaultResolver.resolve(i, r);
    }

    private Object resolve(Injectee i) {
        Binding binding = Bindings.get(i);

        TransactionManager manager = new TransactionManager(locator.getService(binding.contract()));
        locator.inject(manager);

        return binding.contract().cast(
                Proxy.newProxyInstance(Container.class.getClassLoader(), new Class[]{binding.contract()}, manager)
        );
    }

}