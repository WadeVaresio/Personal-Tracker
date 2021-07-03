package com.varesio.wade.personaltracker.entities;

import net.codebox.javabeantester.JavaBeanTester;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.beans.IntrospectionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class SavedNoteTests {
    private SavedNote note1 = new SavedNote(1, "abc", "test note");
    private SavedNote note2 = new SavedNote(1, "abc", "test note");
    private SavedNote note3 = new SavedNote(1, "abc", "Test Note");;

    @BeforeEach
    public void configure(){
        note1 = new SavedNote(1, "abc", "test note");
        note2 = new SavedNote(1, "abc", "test note");
    }

    @Test
    public void test_gettersAndSetters() throws IntrospectionException {
        JavaBeanTester.test(SavedNote.class);
    }

    @Test
    public void test_equals(){
        assertEquals(note1, note2);
    }

    @Test
    public void test_notEquals(){
        assertNotEquals(note1, note3);
    }

    @Test
    public void test_toString(){
        String expected = "{id: " + 1 +
                ", userId: " + "abc" +
                ", note: " + "test note" +
                " }";

        assertEquals(expected, note1.toString());
    }

    @Test
    public void test_equalsHashCode(){
        assertEquals(note1.hashCode(), note2.hashCode());
    }

    @Test
    public void test_notEqualsHashCode(){
        assertNotEquals(note1.hashCode(), note3.hashCode());
    }

}
