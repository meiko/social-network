package com.prettybit.socnet.rest.annotation;

import com.google.common.collect.Iterables;

import javax.ws.rs.core.Form;
import java.lang.annotation.Annotation;

/**
 * @author Pavel Mikhalchuk
 */
public abstract class FormSupportedValueFactoryProvider extends AbstractValueFactoryProvider {

    private Form form;

    protected FormSupportedValueFactoryProvider(Class<? extends Annotation> annotation) {
        super(annotation);
    }

    protected String param(String name) {
        if (form == null) form = request().readEntity(Form.class);
        return form != null ? Iterables.getFirst(form.asMap().get(name), null) : null;
    }

}