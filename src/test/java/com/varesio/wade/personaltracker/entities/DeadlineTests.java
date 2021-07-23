package com.varesio.wade.personaltracker.entities;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class DeadlineTests {
    private Deadline deadline = new Deadline(1L, "test@test.com", new Timestamp(0L), "description");

    @Test
    public void test_getterSetterId(){
        assertEquals(1L, deadline.getId());
        deadline.setId(2L);
        assertEquals(2L, deadline.getId());
    }

    @Test
    public void test_getterSetterUserID(){
        assertEquals("test@test.com", deadline.getUserID());
        deadline.setUserID("test");
        assertEquals("test", deadline.getUserID());
    }

    @Test
    public void test_getterSetterDate(){
        assertEquals(new Timestamp(0L), deadline.getDate());
        deadline.setDate(new Timestamp(2L));
        assertEquals(new Timestamp(2L), deadline.getDate());
    }

    @Test
    public void test_getterSetterDescription(){
        assertEquals("description", deadline.getDescription());
        deadline.setDescription("desc");
        assertEquals("desc", deadline.getDescription());
    }

    @Test
    public void test_Equals(){
        Deadline diffDeadline = new Deadline(1L, "test@test.com", new Timestamp(0L), "description");

        assertEquals(deadline, diffDeadline);

        diffDeadline.setId(2L);

        assertNotEquals(deadline, diffDeadline);
    }

    @Test
    public void test_hashCode(){
        Deadline sameDeadline = new Deadline(1L, "test@test.com", new Timestamp(0L), "description");

        assertEquals(deadline.hashCode(), sameDeadline.hashCode());

        Deadline diffDeadline = new Deadline(2L, "test@test.com", new Timestamp(0L), "description");
        assertNotEquals(deadline.hashCode(), diffDeadline.hashCode());
    }

    @Test
    public void test_toString(){
        String expected = "{id: 1" +
                ", userId: test@test.com" +
                ", date: 1969-12-31 16:00:00.0" +
                ", description: description" +
                " }";

        assertEquals(expected, deadline.toString());
    }

}
