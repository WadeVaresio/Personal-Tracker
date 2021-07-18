package com.varesio.wade.personaltracker.models;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class UserTests {
    private final String testJWTToken = "Bearer eyJhbGciOiJIUzI1NiJ9." +
            "eyJodHRwczovL3Rlc3QtYXBpLmNvbSI6eyJlbWFpbCI6InRlc3RAZW1haWwuY29tIiwiZ2l2ZW5fbmFtZSI6IkpvaG4gRG9lIn0sImlzcyI6Imh0dHBzOi8vZGV2LTEyMy51cy5hdXRoMC5jb20vIiwic3ViIjoiZ29vZ2xlLW9hdXRoMnwxMTY0MTgxNTUxOTI2MzgxNzc0NjIiLCJhdWQiOlsiaHR0cHM6Ly90ZXN0LWF" +
            "waS5jb20iLCJodHRwczovL2Rldi1mZWpmdTgwMi51cy5hdXRoMC5jb20vdXNlcmluZm8iXSwiaWF0IjoxNjI2NjI0ODIxLCJleHAiOjE2MjY3MTEyMjEsImF6cCI6IlFKUkpReFU5NUlvazJ3TlJIbHJndTZnd0dIQVVvUVNTVyIsInNjb3BlIjoib3BlbmlkIHByb2ZpbGUgZW1haWwifQ." +
            "_6WhNmTj5IbYSe0RzfLgv0VEgXSwRApykwFWnwLH4mY";
    private final String testCustomClaimURL = "https://test-api.com";

    private User testUser, testUser2;

    @BeforeEach
    public void setup(){
        testUser = new User(testJWTToken, testCustomClaimURL);

        testUser2 = new User(testJWTToken, testCustomClaimURL);
        testUser2.setGivenName("John Doe 2");
        testUser2.setEmail("testemail2@email.com");
    }

    @Test
    public void test_constructorThrowsRuntimeException() throws RuntimeException{
        Exception exception = assertThrows(RuntimeException.class, () -> {
            new User(testJWTToken, "randomClaim");
        });

        String expectedMessage = "Custom claim: randomClaim is bad";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void test_getGivenName(){
        assertEquals("John Doe", testUser.getGivenName());
    }

    @Test
    public void test_setGivenName(){
        testUser.setGivenName("John Doe 2");
        assertEquals("John Doe 2", testUser.getGivenName());
    }

    @Test
    public void test_getEmail(){
        assertEquals("test@email.com", testUser.getEmail());
    }

    @Test
    public void test_setEmail(){
        testUser.setEmail("testemail2@email.com");
        assertEquals("testemail2@email.com", testUser.getEmail());
    }

    @Test
    public void test_equals(){
        User equalUser = new User(testJWTToken, testCustomClaimURL);
        assertEquals(testUser, equalUser);
    }

    @Test
    public void test_notEqualsDiffName(){
        assertNotEquals(testUser, testUser2);
    }

    @Test
    public void test_notEqualsDiffEmail(){
        assertNotEquals(testUser, testUser2);
    }

    @Test
    public void test_hashCodeEquals(){
        User equalUser = new User(testJWTToken, testCustomClaimURL);
        assertEquals(testUser.hashCode(), equalUser.hashCode());
    }

    @Test
    public void test_hashCodeNotEquals(){
        assertNotEquals(testUser, testUser2);
    }
}
