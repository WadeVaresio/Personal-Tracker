package com.varesio.wade.personaltracker.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class SavedNote {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private long id;

    @Column
    private String userId;


    @Column
    private String note;

    public SavedNote(long id, String userId, String note) {
        this.id = id;
        this.note = note;
    }

    public SavedNote(String userId, String link, String note) {
        this.userId = userId;
        this.note = note;
    }

    public SavedNote() { }

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
        SavedNote savedNote = (SavedNote) o;
        return id == savedNote.id && userId.equals(savedNote.userId) && note.equals(savedNote.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, note);
    }

    @Override
    public String toString(){
        return "{id: " + this.id +
                ", userId: " + this.userId +
                ", note: " + this.note;
    }
}
