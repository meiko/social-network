package com.prettybit.framework.container.bind;

import org.glassfish.hk2.api.Injectee;

import java.util.LinkedList;
import java.util.List;

import static com.google.common.collect.Collections2.filter;
import static com.google.common.collect.Iterables.getFirst;
import static com.prettybit.framework.container.bind.Binding.byInjectee;
import static java.util.Collections.unmodifiableList;

/**
 * @author Pavel Mikhalchuk
 */
public class Bindings {

    private static List<Binding> bindings = new LinkedList<Binding>();

    private static boolean locked = false;

    public static Binding bind(Class<?> service) {
        if (locked) return null;
        Binding b = new Binding(service);
        bindings.add(b);
        return b;
    }

    public static List<Binding> list() { return unmodifiableList(bindings); }

    public static Binding get(Injectee i) { return getFirst(filter(Bindings.list(), byInjectee(i)), null); }

    public static boolean has(Injectee i) { return get(i) != null; }

    public static void lock() { locked = true; }

}