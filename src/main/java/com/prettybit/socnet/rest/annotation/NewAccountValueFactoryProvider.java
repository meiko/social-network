package com.prettybit.socnet.rest.annotation;

import com.prettybit.socnet.entity.Account;
import org.glassfish.hk2.api.Factory;

/**
 * @author Pavel Mikhalchuk
 */
public class NewAccountValueFactoryProvider extends FormSupportedValueFactoryProvider {

    public NewAccountValueFactoryProvider() {
        super(NewAccount.class);
    }

    @Override
    public Factory<?> createFactory() {
        return new AbstractValueFactory<Account>() {
            @Override
            public Account provide() {
                Account account = new Account();
                account.setName(param("name"));
                account.setEmail(param("email"));
                account.setPassword(param("password"));
                return account;
            }
        };
    }

}