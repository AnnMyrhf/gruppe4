package com.cityfeedback.backend.security;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtResponseTest {

    @Test
    void testSetToken() {
        JwtResponse jwtResponse = new JwtResponse(null, null, null, null);
        String newToken = "newToken";

        jwtResponse.setAccessToken(newToken);

        assertEquals(newToken, jwtResponse.getAccessToken(), "The token should be updated to the new value");
    }

    @Test
    void testSetType() {
        JwtResponse jwtResponse = new JwtResponse(null, null, null, null);
        String newType = "CustomType";

        jwtResponse.setTokenType(newType);

        assertEquals(newType, jwtResponse.getTokenType(), "The type should be updated to the new value");
    }

    @Test
    void testCanEqual() {
        JwtResponse jwtResponse1 = new JwtResponse("token1", 1L, "email1@example.com", new Object[]{"ROLE_USER"});
        JwtResponse jwtResponse2 = new JwtResponse("token1", 1L, "email1@example.com", new Object[]{"ROLE_USER"});

        assertTrue(jwtResponse1.canEqual(jwtResponse2), "Objects of the same type should be able to equal each other");
        assertFalse(jwtResponse1.canEqual(new Object()), "Objects of different types should not be able to equal each other");
    }

    @Test
    void testEqualsSameObject() {
        JwtResponse jwtResponse = new JwtResponse("token", 1L, "email@example.com", new Object[]{"ROLE_USER"});

        assertEquals(jwtResponse, jwtResponse, "An object should be equal to itself");
    }

    @Test
    void testEqualsDifferentObjectsNotEqual() {
        JwtResponse jwtResponse1 = new JwtResponse("token1", 1L, "email1@example.com", new Object[]{"ROLE_USER"});
        JwtResponse jwtResponse2 = new JwtResponse("token2", 2L, "email2@example.com", new Object[]{"ROLE_ADMIN"});

        assertNotEquals(jwtResponse1, jwtResponse2, "Objects with different states should not be equal");
    }

    @Test
    void testHashCodeDifferentObjects() {
        JwtResponse jwtResponse1 = new JwtResponse("token1", 1L, "email1@example.com", new Object[]{"ROLE_USER"});
        JwtResponse jwtResponse2 = new JwtResponse("token2", 2L, "email2@example.com", new Object[]{"ROLE_ADMIN"});

        assertNotEquals(jwtResponse1.hashCode(), jwtResponse2.hashCode(), "Different objects should have different hash codes");
    }

    @Test
    void testConstructorAndGetters() {
        String accessToken = "sampleToken";
        Long id = 123L;
        String email = "test@example.com";
        Object[] roles = {"ROLE_USER", "ROLE_ADMIN"};

        JwtResponse jwtResponse = new JwtResponse(accessToken, id, email, roles);

        assertEquals(accessToken, jwtResponse.getAccessToken());
        assertEquals("Bearer", jwtResponse.getTokenType());
        assertEquals(id, jwtResponse.getId());
        assertEquals(email, jwtResponse.getEmail());
        assertArrayEquals(roles, (Object[]) jwtResponse.getRole());
    }

    @Test
    void testSetters() {
        JwtResponse jwtResponse = new JwtResponse(null, null, null, null);
        String newToken = "newToken";
        String newType = "NewType";
        Long newId = 456L;
        String newEmail = "new@example.com";
        Object[] newRoles = {"ROLE_NEW"};

        jwtResponse.setAccessToken(newToken);
        jwtResponse.setTokenType(newType);
        jwtResponse.setId(newId);
        jwtResponse.setEmail(newEmail);
        jwtResponse.setRole(newRoles);

        assertEquals(newToken, jwtResponse.getAccessToken());
        assertEquals(newType, jwtResponse.getTokenType());
        assertEquals(newId, jwtResponse.getId());
        assertEquals(newEmail, jwtResponse.getEmail());
        assertArrayEquals(newRoles, (Object[]) jwtResponse.getRole());
    }

    @Test
    void testDefaultType() {
        String accessToken = "defaultToken";
        Long id = 789L;
        String email = "default@example.com";
        Object[] roles = {"ROLE_DEFAULT"};

        JwtResponse jwtResponse = new JwtResponse(accessToken, id, email, roles);

        assertEquals("Bearer", jwtResponse.getTokenType());
    }

    @Test
    void testEqualsWithItself() {
        JwtResponse jwtResponse = new JwtResponse("token", 1L, "email@example.com", new Object[]{"ROLE_USER"});
        assertEquals(jwtResponse, jwtResponse, "Ein Objekt sollte immer gleich sich selbst sein");
    }


    @Test
    void testEqualsWithDifferentObjects() {
        JwtResponse jwtResponse1 = new JwtResponse("token", 1L, "email@example.com", new Object[]{"ROLE_USER"});
        JwtResponse jwtResponse2 = new JwtResponse("differentToken", 1L, "email@example.com", new Object[]{"ROLE_USER"});
        assertNotEquals(jwtResponse1, jwtResponse2, "Objekte mit unterschiedlichen Attributwerten sollten nicht gleich sein");
    }

    @Test
    void testEqualsWithDifferentId() {
        JwtResponse jwtResponse1 = new JwtResponse("token", 1L, "email@example.com", new Object[]{"ROLE_USER"});
        JwtResponse jwtResponse2 = new JwtResponse("token", 2L, "email@example.com", new Object[]{"ROLE_USER"});
        assertNotEquals(jwtResponse1, jwtResponse2, "Objekte mit unterschiedlicher ID sollten nicht gleich sein");
    }

    @Test
    void testEqualsWithDifferentEmail() {
        JwtResponse jwtResponse1 = new JwtResponse("token", 1L, "email@example.com", new Object[]{"ROLE_USER"});
        JwtResponse jwtResponse2 = new JwtResponse("token", 1L, "other@example.com", new Object[]{"ROLE_USER"});
        assertNotEquals(jwtResponse1, jwtResponse2, "Objekte mit unterschiedlicher E-Mail sollten nicht gleich sein");
    }

    @Test
    void testEqualsWithDifferentRole() {
        JwtResponse jwtResponse1 = new JwtResponse("token", 1L, "email@example.com", new Object[]{"ROLE_USER"});
        JwtResponse jwtResponse2 = new JwtResponse("token", 1L, "email@example.com", new Object[]{"ROLE_ADMIN"});
        assertNotEquals(jwtResponse1, jwtResponse2, "Objekte mit unterschiedlicher Rolle sollten nicht gleich sein");
    }

    @Test
    void testEqualsWithDifferentType() {
        JwtResponse jwtResponse1 = new JwtResponse("token", 1L, "email@example.com", new Object[]{"ROLE_USER"});
        JwtResponse jwtResponse2 = new JwtResponse("token", 1L, "email@example.com", new Object[]{"ROLE_USER"});
        jwtResponse2.setTokenType("CustomType");
        assertNotEquals(jwtResponse1, jwtResponse2, "Objekte mit unterschiedlichem Token-Typ sollten nicht gleich sein");
    }

    @Test
    void testEqualsWithDifferentClass() {
        JwtResponse jwtResponse = new JwtResponse("token", 1L, "email@example.com", new Object[]{"ROLE_USER"});
        Object otherObject = new Object();
        assertNotEquals(jwtResponse, otherObject, "Objekte unterschiedlicher Klassen sollten nicht gleich sein");
    }

    @Test
    void testEqualsWithNull() {
        JwtResponse jwtResponse = new JwtResponse("token", 1L, "email@example.com", new Object[]{"ROLE_USER"});
        assertNotEquals(null, jwtResponse, "Ein Objekt sollte nicht gleich null sein");
    }

    @Test
    void testEqualsWithDifferentLengthRoles() {
        JwtResponse jwtResponse1 = new JwtResponse("token", 1L, "email@example.com", new Object[]{"ROLE_USER"});
        JwtResponse jwtResponse2 = new JwtResponse("token", 1L, "email@example.com", new Object[]{"ROLE_USER", "ROLE_ADMIN"});
        assertNotEquals(jwtResponse1, jwtResponse2, "Objekte mit unterschiedlicher Länge des Rollen-Arrays sollten nicht gleich sein");
    }

}
