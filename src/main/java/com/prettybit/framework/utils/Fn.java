package com.prettybit.framework.utils;

import com.google.common.base.Function;

/**
 * @author Pavel Mikhalchuk
 */
public class Fn {

    public static abstract class SimpleFunction<T> implements Function<Void, T> {
        @Override
        public T apply(Void input) { return apply(); }

        public abstract T apply();
    }

}