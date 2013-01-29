package com.prettybit.socnet.rest.annotation;

import com.prettybit.socnet.entity.Account;
import org.glassfish.hk2.api.Factory;

/**
 * @author Pavel Mikhalchuk
 */
public class NewAccountValueFactoryProvider extends AbstractValueFactoryProvider {

    @Override
    public Factory<?> createFactory() {
        return new AbstractValueFactory<Account>() {
            @Override
            public Account provide() {
                Account account = new Account();
                account.setName(request().getParameter("name"));
                account.setEmail(request().getParameter("email"));
                account.setPassword(request().getParameter("password"));
                return account;
            }
        };
    }

}