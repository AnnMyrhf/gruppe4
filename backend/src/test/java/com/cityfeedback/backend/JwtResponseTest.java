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

    @Test
    void testConstructorAndGetters() {
        // Arrange
        String accessToken = "sampleToken";
        Long id = 123L;
        String email = "test@example.com";
        Object[] roles = {"ROLE_USER", "ROLE_ADMIN"};

        // Act
        JwtResponse jwtResponse = new JwtResponse(accessToken, id, email, roles);

        // Assert
        assertEquals(accessToken, jwtResponse.getAccessToken());
        assertEquals("Bearer", jwtResponse.getTokenType());
        assertEquals(id, jwtResponse.getId());
        assertEquals(email, jwtResponse.getEmail());
        assertArrayEquals(roles, (Object[]) jwtResponse.getRole());
    }

    @Test
    void testSetters() {
        // Arrange
        JwtResponse jwtResponse = new JwtResponse(null, null, null, null);
        String newToken = "newToken";
        String newType = "NewType";
        Long newId = 456L;
        String newEmail = "new@example.com";
        Object[] newRoles = {"ROLE_NEW"};

        // Act
        jwtResponse.setAccessToken(newToken);
        jwtResponse.setTokenType(newType);
        jwtResponse.setId(newId);
        jwtResponse.setEmail(newEmail);
        jwtResponse.setRole(newRoles);

        // Assert
        assertEquals(newToken, jwtResponse.getAccessToken());
        assertEquals(newType, jwtResponse.getTokenType());
        assertEquals(newId, jwtResponse.getId());
        assertEquals(newEmail, jwtResponse.getEmail());
        assertArrayEquals(newRoles, (Object[]) jwtResponse.getRole());
    }

    @Test
    void testDefaultType() {
        // Arrange
        String accessToken = "defaultToken";
        Long id = 789L;
        String email = "default@example.com";
        Object[] roles = {"ROLE_DEFAULT"};

        // Act
        JwtResponse jwtResponse = new JwtResponse(accessToken, id, email, roles);

        // Assert
        assertEquals("Bearer", jwtResponse.getTokenType());
    }
}

