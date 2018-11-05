package com.project.project1.Entity;

import com.sun.xml.internal.messaging.saaj.util.LogDomainConstants;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "videos")
public class Videos implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;
    @Column(name = "UId")
    private String uid;
    @Column(name = "Video_Name")
    private String video;

    public Videos( String uid, String video){
        this.uid = uid;
        this.video = video;
    }

    public Videos() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
}
