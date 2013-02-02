package com.prettybit.framework;

import org.glassfish.hk2.api.Factory;

/**
 * @author Pavel Mikhalchuk
 */
public abstract class AbstractValueFactory<T> implements Factory<T> {

    @Override
    public void dispose(T instance) {
    }

}