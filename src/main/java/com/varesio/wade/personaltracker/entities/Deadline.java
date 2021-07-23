package com.varesio.wade.personaltracker.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class Deadline {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private long id;

    @Column(nullable = false)
    private String userID;

    @Column
    private Timestamp date;

    @Column(nullable = false)
    private String description;

    public Deadline(long id, String userID, Timestamp date, String description) {
        this.id = id;
        this.userID = userID;
        this.date = date;
        this.description = description;
    }

    public Deadline(){}


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Deadline deadline = (Deadline) o;
        return id == deadline.id && userID.equals(deadline.userID) && Objects.equals(date, deadline.date) && description.equals(deadline.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userID, date, description);
    }

    @Override
    public String toString(){
        return "{id: " + this.id +
                ", userId: " + this.userID +
                ", date: " + this.date +
                ", description: " + this.description +
                " }";
    }
}
