package com.social.security.model.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class MainEntity {
    
    @Column(name = "ip_adress")
    private String ipAddress;

    @Column(name = "creation_at")
    private Date createdAt;

    @Column(name = "modified_at")
    private Date modifiedAt;

}
