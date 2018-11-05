package com.project.project1.Entity;


import com.sun.xml.internal.messaging.saaj.util.LogDomainConstants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = " urlshortener")
public class URLShortener implements LogDomainConstants {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "originalurl")
    private String originalURL;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getOriginalURL() {
        return originalURL;
    }

    public void setOriginalURL(String originalURL) {
        this.originalURL = originalURL;
    }


}
