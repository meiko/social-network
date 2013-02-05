package com.prettybit.framework.container.jersey;

import org.glassfish.hk2.api.InjectionResolver;

/**
 * @author Pavel Mikhalchuk
 */
public abstract class AbstractInjectionResolver<T> implements InjectionResolver<T> {

    private boolean constructorParameterIndicator;
    private boolean methodParameterIndicator;

    protected AbstractInjectionResolver(boolean constructorParameterIndicator, boolean methodParameterIndicator) {
        this.constructorParameterIndicator = constructorParameterIndicator;
        this.methodParameterIndicator = methodParameterIndicator;
    }

    @Override
    public boolean isConstructorParameterIndicator() { return constructorParameterIndicator; }

    @Override
    public boolean isMethodParameterIndicator() { return methodParameterIndicator; }

}