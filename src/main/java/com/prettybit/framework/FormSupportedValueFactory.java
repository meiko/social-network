package com.prettybit.framework;

import javax.inject.Inject;
import javax.ws.rs.core.Form;

import static com.prettybit.framework.utils.Fn.SimpleFunction;
import static org.glassfish.jersey.server.internal.inject.HttpContext.FORM_PROPERTY;

/**
 * @author Pavel Mikhalchuk
 */
public abstract class FormSupportedValueFactory<T> extends AbstractValueFactory<T> {

    @Inject
    private RequestContext c;

    protected String param(String name) {
        return c.getOrSet(FORM_PROPERTY, readForm()).asMap().getFirst(name);
    }

    private SimpleFunction<Form> readForm() {
        return new SimpleFunction<Form>() {
            @Override
            public Form apply() { return c.request().readEntity(Form.class); }
        };
    }

}