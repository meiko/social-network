package com.prettybit.socnet.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Pavel Mikhalchuk
 */
@Entity
@Table(name = "account")
public class Account extends BaseEntity {

    @Column(nullable = false)
    private String name;

    private String email;

    @Column(nullable = false, length = 20)
    private String password;

}