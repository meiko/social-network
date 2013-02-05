package com.prettybit.socnet.be;

import com.prettybit.framework.container.jpa.Transactional;
import com.prettybit.socnet.be.exception.AuthorizationFailedException;
import com.prettybit.socnet.entity.Account;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * @author Pavel Mikhalchuk
 */
public class AccountServiceImpl implements AccountService {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void create(Account account) {
        em.persist(account);
    }

    @Override
    public Account authorize(String email, String password) throws AuthorizationFailedException {
        Query q = em.createQuery("select a from Account a where a.email = :email and a.password = :password");
        q.setParameter("email", email);
        q.setParameter("password", password);
        try {
            return (Account) q.getSingleResult();
        } catch (NoResultException e) {
            throw new AuthorizationFailedException();
        }
    }

}