package com.prettybit.framework.container.jpa;

import com.prettybit.framework.RequestContext;
import com.prettybit.framework.container.Container;
import com.prettybit.framework.utils.Fn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * @author Pavel Mikhalchuk
 */
public class PersistenceContextProvider {

    private Logger log = LoggerFactory.getLogger(PersistenceContextProvider.class);

    public static final String EM = "javax.persistence.EntityManager";

    @Inject
    private RequestContext c;

    public EntityManager get() {
        return c.getOrSet(EM, newEntityManager());
    }

    public void close() {
        get().close();
    }

    private Fn.SimpleFunction<EntityManager> newEntityManager() {
        return new Fn.SimpleFunction<EntityManager>() {
            @Override
            public EntityManager apply() {
                EntityManager em = Container.newEntityManager();
                log.debug("Entity Manager created [" + em + "]");
                return em;
            }
        };
    }

}