package com.project.project1.Entity;

import org.springframework.stereotype.Component;

import javax.persistence.Entity;

@Component
public class URLShortenerDTO {
    private String id;
    private String originalURL;
    private String shortenedURL;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOriginalURL() {
        return originalURL;
    }

    public void setOriginalURL(String originalURL) {
        this.originalURL = originalURL;
    }


    public String getShortenedURL() {
        return shortenedURL;
    }

    public void setShortenedURL(String shortenedURL) {
        this.shortenedURL = shortenedURL;
    }


}
