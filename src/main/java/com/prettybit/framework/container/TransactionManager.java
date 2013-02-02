package com.prettybit.framework.container;

import org.glassfish.jersey.server.model.Invocable;
import org.glassfish.jersey.server.spi.internal.ResourceMethodInvocationHandlerProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.persistence.EntityTransaction;
import javax.persistence.RollbackException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Pavel Mikhalchuk
 */
public class TransactionManager implements ResourceMethodInvocationHandlerProvider {

    private Logger log = LoggerFactory.getLogger(TransactionManager.class);

    @Inject
    private PersistenceContextProvider emProvider;

    @Override
    public InvocationHandler create(Invocable method) {
        return isTransactional(method) ? createManager() : null;
    }

    private boolean isTransactional(Invocable method) {
        return method.getHandler().getHandlerClass().isAnnotationPresent(Transactional.class) ||
                method.getHandlingMethod().isAnnotationPresent(Transactional.class);
    }

    private InvocationHandler createManager() {
        return new InvocationHandler() {
            @Override
            public Object invoke(Object target, Method method, Object[] args) throws Throwable {
                try {
                    begin();
                    Object result = doInvoke(target, method, args);
                    commit();
                    return result;
                } catch (Exception e) {
                    rollback(e);
                    throw new RollbackException("Transaction was rolled back due invocation error.", e);
                }
            }
        };
    }

    private void begin() {
        transaction().begin();
        log.debug("New transaction begun.");
    }

    private Object doInvoke(Object target, Method method, Object[] args) throws IllegalAccessException, InvocationTargetException {
        log.debug("[" + method.getName() + "] method is about to be invoked.");
        return method.invoke(target, args);
    }

    private void commit() {
        transaction().commit();
        log.debug("Transaction complete.");
    }

    private void rollback(Exception e) {
        if (transaction().isActive()) {
            log.debug("Rolling back the transaction.", e);
            transaction().rollback();
        }
    }

    private EntityTransaction transaction() {
        return emProvider.get().getTransaction();
    }

}