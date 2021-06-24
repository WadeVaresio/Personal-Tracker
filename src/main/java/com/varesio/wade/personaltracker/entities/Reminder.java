package com.varesio.wade.personaltracker.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Reminder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private String userId;

    @Column
    private String link;

    @Column
    private String note;

    public Reminder(long id, String userId, String link, String note) {
        this.id = id;
        this.userId = userId;
        this.link = link;
        this.note = note;
    }

    public Reminder(String userId, String link, String note) {
        this.userId = userId;
        this.link = link;
        this.note = note;
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
        Reminder reminder = (Reminder) o;
        return id == reminder.id && userId.equals(reminder.userId) && link.equals(reminder.link) && note.equals(reminder.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, link, note);
    }
}
