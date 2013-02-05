package com.prettybit.framework.container.bind;

import com.google.common.base.Predicate;
import org.glassfish.hk2.api.Injectee;

import java.lang.annotation.Annotation;

/**
 * @author Pavel Mikhalchuk
 */
public class Binding {

    private Class<?> contract;
    private Class<?> service;
    private Class<? extends Annotation> scope;

    public Binding(Class<?> service) {
        this.service = service;
    }

    public Binding to(Class<?> contract) {
        this.contract = contract;
        return this;
    }

    public Binding in(Class<? extends Annotation> scope) {
        this.scope = scope;
        return this;
    }

    public Class<?> contract() {
        return contract;
    }

    public Class<?> service() {
        return service;
    }

    public Class<? extends Annotation> scope() {
        return scope;
    }

    public static Predicate<? super Binding> byInjectee(final Injectee i) {
        return new Predicate<Binding>() {
            @Override
            public boolean apply(Binding b) {
                return b.contract().equals(i.getRequiredType());
            }
        };
    }

}