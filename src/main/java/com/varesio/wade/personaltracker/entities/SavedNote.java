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
    private String userID;


    @Column
    private String note;

    public SavedNote(long id, String userID, String note) {
        this.id = id;
        this.userID = userID;
        this.note = note;
    }

    public SavedNote() { }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userId) {
        this.userID = userId;
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
        return id == savedNote.id && note.equals(savedNote.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userID, note);
    }

    @Override
    public String toString(){
        return "{id: " + this.id +
                ", userId: " + this.userID +
                ", note: " + this.note +
                " }";
    }
}
