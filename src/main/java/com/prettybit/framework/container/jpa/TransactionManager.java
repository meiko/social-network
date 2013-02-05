package com.prettybit.framework.container.jpa;

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
public class TransactionManager implements InvocationHandler {

    private Logger log = LoggerFactory.getLogger(TransactionManager.class);

    private Object target;

    public TransactionManager(Object target) {
        this.target = target;
    }

    @Inject
    private PersistenceContextProvider emProvider;

    @Override
    public Object invoke(Object target, Method method, Object[] args) throws Throwable {
        if (isTransactional(method)) {
            return invokeInTransaction(method, args);
        } else {
            return doInvoke(method, args);
        }
    }

    private boolean isTransactional(Method m) {
        try {
            m = target.getClass().getMethod(m.getName(), m.getParameterTypes());
            return target.getClass().isAnnotationPresent(Transactional.class) || m.isAnnotationPresent(Transactional.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private Object invokeInTransaction(Method method, Object[] args) {
        try {
            begin();
            Object result = doInvoke(method, args);
            commit();
            return result;
        } catch (Throwable e) {
            rollback(e);
            throw new RollbackException("Transaction was rolled back due invocation error.", e);
        }
    }

    private void begin() {
        transaction().begin();
        log.debug("New transaction begun.");
    }

    private Object doInvoke(Method method, Object[] args) throws Throwable {
        log.debug("[" + method.getName() + "] method is about to be invoked.");
        try {
            return method.invoke(this.target, args);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }

    private void commit() {
        transaction().commit();
        log.debug("Transaction complete.");
    }

    private void rollback(Throwable e) {
        if (transaction().isActive()) {
            log.debug("Rolling back the transaction.", e);
            transaction().rollback();
        }
    }

    private EntityTransaction transaction() {
        return emProvider.get().getTransaction();
    }

}