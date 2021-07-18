package com.varesio.wade.personaltracker.models;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Map;
import java.util.Objects;

public class User {
    private final int JWT_START_INDEX = 7;

    private String givenName;

    private String email;

    public User(String jwtToken, String customClaimURL){
        DecodedJWT decodedJWT = JWT.decode(jwtToken.substring(JWT_START_INDEX));
        Map<String, Object> claims = decodedJWT.getClaim(customClaimURL).asMap();

        if(claims == null)
            throw new RuntimeException("Custom claim: " + customClaimURL + " is bad");

        this.givenName = (String) claims.get("given_name");
        this.email = (String) claims.get("email");
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return givenName.equals(user.givenName) && email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(givenName, email);
    }
}
