package com.varesio.wade.personaltracker.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class SavedLink {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private long id;

    @Column
    private String userId;

    @Column
    private String link;

    @Column
    private String note;

    public SavedLink(long id, String userId, String link, String note) {
        this.id = id;
        this.userId = userId;
        this.link = link;
        this.note = note;
    }

    public SavedLink(String userId, String link, String note) {
        this.userId = userId;
        this.link = link;
        this.note = note;
    }

    public SavedLink() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SavedLink savedLink = (SavedLink) o;
        return id == savedLink.id && userId.equals(savedLink.userId) && this.link.equals(savedLink.link) && note.equals(savedLink.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, link, note);
    }

    @Override
    public String toString(){
        return "{id: " + this.id +
                ", userId: " + this.userId +
                ", link: " + this.link +
                ", note: " + this.note;
    }
}
