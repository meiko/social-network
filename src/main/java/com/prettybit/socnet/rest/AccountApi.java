package com.prettybit.socnet.rest;

import com.prettybit.socnet.be.AccountService;
import com.prettybit.socnet.be.exception.AuthorizationFailedException;
import com.prettybit.socnet.entity.Account;
import com.prettybit.socnet.rest.annotation.NewAccount;

import javax.inject.Inject;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import static org.apache.commons.lang.StringUtils.isBlank;

/**
 * @author Pavel Mikhalchuk
 */
@Path("account")
public class AccountApi {

    @Inject
    private AccountService s;

    @POST
    @Path("create")
    public void create(@NewAccount Account account) {
        s.create(account);
    }

    @GET
    @Path("login")
    public Account login(@CookieParam("email") String email, @CookieParam("password") String password) {
        try {
            if (isBlank(email) || isBlank(password)) throw new NotAuthorizedException("[Soc Net]: missing credentials.");
            return s.authorize(email, password);
        } catch (AuthorizationFailedException e) {
            throw new NotAuthorizedException("[Soc Net]: Invalid login/password.");
        }
    }

}