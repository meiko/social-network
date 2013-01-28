package com.prettybit.socnet.entity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author Pavel Mikhalchuk
 */
public class Test {

    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("soc-net");
        EntityManager manager = factory.createEntityManager();
    }

}