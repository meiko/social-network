package com.prettybit.socnet.rest;

import com.prettybit.socnet.entity.Account;
import com.prettybit.socnet.rest.annotation.NewAccount;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * @author Pavel Mikhalchuk
 */
@Path("account")
public class AccountApi {

    @PersistenceContext
    private EntityManager em;

    @Path("create")
    @POST
    public void create(@NewAccount Account account) {
        em.persist(account);
    }

}