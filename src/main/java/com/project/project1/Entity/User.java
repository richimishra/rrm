package com.project.project1.Entity;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User {

    @Id
    private String uid;

    @ManyToOne
    @JoinColumn(name = "uid",insertable=false, updatable=false)
    private Videos videos;


    public Videos getVideos() {
        return videos;
    }

    public void setVideos(Videos videos) {
        this.videos = videos;
    }

    public User() {
    }

    public String getUser_id() {
        return uid;
    }

    public void setUser_id(String user_id) {
        this.uid = user_id;
    }
}
