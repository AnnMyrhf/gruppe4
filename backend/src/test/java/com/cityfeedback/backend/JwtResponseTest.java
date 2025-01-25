package com.cityfeedback.backend.security;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Objects;

class JwtResponseTest {

    @Test
    void testSetToken() {
        // Arrange
        JwtResponse jwtResponse = new JwtResponse(null, null, null, null);
        String newToken = "newToken";

        // Act
        jwtResponse.setAccessToken(newToken);

        // Assert
        assertEquals(newToken, jwtResponse.getAccessToken(), "The token should be updated to the new value");
    }

    @Test
    void testSetType() {
        // Arrange
        JwtResponse jwtResponse = new JwtResponse(null, null, null, null);
        String newType = "CustomType";

        // Act
        jwtResponse.setTokenType(newType);

        // Assert
        assertEquals(newType, jwtResponse.getTokenType(), "The type should be updated to the new value");
    }

    @Test
    void testCanEqual() {
        // Arrange
        JwtResponse jwtResponse1 = new JwtResponse("token1", 1L, "email1@example.com", new Object[]{"ROLE_USER"});
        JwtResponse jwtResponse2 = new JwtResponse("token1", 1L, "email1@example.com", new Object[]{"ROLE_USER"});

        // Act & Assert
        assertTrue(jwtResponse1.canEqual(jwtResponse2), "Objects of the same type should be able to equal each other");
        assertFalse(jwtResponse1.canEqual(new Object()), "Objects of different types should not be able to equal each other");
    }
}
