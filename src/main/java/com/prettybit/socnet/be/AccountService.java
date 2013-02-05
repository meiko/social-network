package com.prettybit.socnet.be;

import com.prettybit.socnet.be.exception.AuthorizationFailedException;
import com.prettybit.socnet.entity.Account;

/**
 * @author Pavel Mikhalchuk
 */
public interface AccountService {

    void create(Account account);

    Account authorize(String email, String password) throws AuthorizationFailedException;

}