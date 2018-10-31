package com.project.project1.Entity;

import javax.persistence.*;

@Entity
@Table(name= "media")
public class Videos {
    @Id
    private String uid;
    private String video;

    @ManyToOne
    @JoinColumn(name="uid")
    private User user;


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public Videos(String uid, String video) {
        this.uid = uid;
        this.video = video;
    }
}
