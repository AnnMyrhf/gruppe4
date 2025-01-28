package com.cityfeedback.backend.security;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtResponseTest {

    /**
     * Testet das Setzen eines neuen Tokens und überprüft, ob es korrekt aktualisiert wird
     */
    @Test
    void testSetToken() {
        JwtResponse jwtResponse = new JwtResponse(null, null, null, null);
        String newToken = "newToken";

        jwtResponse.setAccessToken(newToken);

        assertEquals(newToken, jwtResponse.getAccessToken(), "Das Token sollte auf den neuen Wert aktualisiert werden");
    }

    /**
     * Testet das Setzen eines neuen Token-Typs und überprüft, ob er korrekt aktualisiert wird
     */
    @Test
    void testSetType() {
        JwtResponse jwtResponse = new JwtResponse(null, null, null, null);
        String newType = "CustomType";

        jwtResponse.setTokenType(newType);

        assertEquals(newType, jwtResponse.getTokenType(), "Der Typ sollte auf den neuen Wert aktualisiert werden");
    }

    /**
     * Testet die canEqual-Methode, um zu überprüfen, ob Objekte des gleichen Typs miteinander verglichen werden können
     */
    @Test
    void testCanEqual() {
        JwtResponse jwtResponse1 = new JwtResponse("token1", 1L, "email1@example.com", new Object[]{"ROLE_USER"});
        JwtResponse jwtResponse2 = new JwtResponse("token1", 1L, "email1@example.com", new Object[]{"ROLE_USER"});

        assertTrue(jwtResponse1.canEqual(jwtResponse2), "Objekte des gleichen Typs sollten miteinander verglichen werden können");
        assertFalse(jwtResponse1.canEqual(new Object()), "Objekte unterschiedlicher Typen sollten nicht miteinander verglichen werden können");
    }

    /**
     * Testet, ob ein Objekt gleich sich selbst ist
     */
    @Test
    void testEqualsSameObject() {
        JwtResponse jwtResponse = new JwtResponse("token", 1L, "email@example.com", new Object[]{"ROLE_USER"});

        assertEquals(jwtResponse, jwtResponse, "Ein Objekt sollte immer gleich sich selbst sein");
    }

    /**
     * Testet, ob zwei unterschiedliche Objekte nicht gleich sind
     */
    @Test
    void testEqualsDifferentObjectsNotEqual() {
        JwtResponse jwtResponse1 = new JwtResponse("token1", 1L, "email1@example.com", new Object[]{"ROLE_USER"});
        JwtResponse jwtResponse2 = new JwtResponse("token2", 2L, "email2@example.com", new Object[]{"ROLE_ADMIN"});

        assertNotEquals(jwtResponse1, jwtResponse2, "Objekte mit unterschiedlichen Attributwerten sollten nicht gleich sein");
    }

    /**
     * Testet, ob zwei unterschiedliche Objekte auch unterschiedliche HashCodes haben
     */
    @Test
    void testHashCodeDifferentObjects() {
        JwtResponse jwtResponse1 = new JwtResponse("token1", 1L, "email1@example.com", new Object[]{"ROLE_USER"});
        JwtResponse jwtResponse2 = new JwtResponse("token2", 2L, "email2@example.com", new Object[]{"ROLE_ADMIN"});

        assertNotEquals(jwtResponse1.hashCode(), jwtResponse2.hashCode(), "Unterschiedliche Objekte sollten unterschiedliche HashCodes haben");
    }

    /**
     * Testet den Konstruktor und die Getter-Methoden
     */
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

    /**
     * Testet die Setter-Methoden
     */
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

    /**
     * Testet den Standardwert des Token-Typs
     */
    @Test
    void testDefaultType() {
        String accessToken = "defaultToken";
        Long id = 789L;
        String email = "default@example.com";
        Object[] roles = {"ROLE_DEFAULT"};

        JwtResponse jwtResponse = new JwtResponse(accessToken, id, email, roles);

        assertEquals("Bearer", jwtResponse.getTokenType());
    }

    /**
     * Testet, ob ein Objekt immer gleich sich selbst ist
     */
    @Test
    void testEqualsWithItself() {
        JwtResponse jwtResponse = new JwtResponse("token", 1L, "email@example.com", new Object[]{"ROLE_USER"});
        assertEquals(jwtResponse, jwtResponse, "Ein Objekt sollte immer gleich sich selbst sein");
    }

    /**
     * Testet, ob zwei Objekte mit unterschiedlichen Attributen nicht gleich sind
     */
    @Test
    void testEqualsWithDifferentObjects() {
        JwtResponse jwtResponse1 = new JwtResponse("token", 1L, "email@example.com", new Object[]{"ROLE_USER"});
        JwtResponse jwtResponse2 = new JwtResponse("differentToken", 1L, "email@example.com", new Object[]{"ROLE_USER"});
        assertNotEquals(jwtResponse1, jwtResponse2, "Objekte mit unterschiedlichen Attributwerten sollten nicht gleich sein");
    }

    /**
     * Testet, ob zwei Objekte mit unterschiedlicher ID nicht gleich sind
     */
    @Test
    void testEqualsWithDifferentId() {
        JwtResponse jwtResponse1 = new JwtResponse("token", 1L, "email@example.com", new Object[]{"ROLE_USER"});
        JwtResponse jwtResponse2 = new JwtResponse("token", 2L, "email@example.com", new Object[]{"ROLE_USER"});
        assertNotEquals(jwtResponse1, jwtResponse2, "Objekte mit unterschiedlicher ID sollten nicht gleich sein");
    }

    /**
     * Testet, ob zwei Objekte mit unterschiedlicher E-Mail nicht gleich sind
     */
    @Test
    void testEqualsWithDifferentEmail() {
        JwtResponse jwtResponse1 = new JwtResponse("token", 1L, "email@example.com", new Object[]{"ROLE_USER"});
        JwtResponse jwtResponse2 = new JwtResponse("token", 1L, "other@example.com", new Object[]{"ROLE_USER"});
        assertNotEquals(jwtResponse1, jwtResponse2, "Objekte mit unterschiedlicher E-Mail sollten nicht gleich sein");
    }

    /**
     * Testet, ob zwei Objekte mit unterschiedlicher Rolle nicht gleich sind
     */
    @Test
    void testEqualsWithDifferentRole() {
        JwtResponse jwtResponse1 = new JwtResponse("token", 1L, "email@example.com", new Object[]{"ROLE_USER"});
        JwtResponse jwtResponse2 = new JwtResponse("token", 1L, "email@example.com", new Object[]{"ROLE_ADMIN"});
        assertNotEquals(jwtResponse1, jwtResponse2, "Objekte mit unterschiedlicher Rolle sollten nicht gleich sein");
    }

    /**
     * Testet, ob zwei Objekte mit unterschiedlichem Token-Typ nicht gleich sind
     */
    @Test
    void testEqualsWithDifferentType() {
        JwtResponse jwtResponse1 = new JwtResponse("token", 1L, "email@example.com", new Object[]{"ROLE_USER"});
        JwtResponse jwtResponse2 = new JwtResponse("token", 1L, "email@example.com", new Object[]{"ROLE_USER"});
        jwtResponse2.setTokenType("CustomType");
        assertNotEquals(jwtResponse1, jwtResponse2, "Objekte mit unterschiedlichem Token-Typ sollten nicht gleich sein");
    }

    /**
     * Testet, ob ein Objekt ungleich einem Objekt einer anderen Klasse ist
     */
    @Test
    void testEqualsWithDifferentClass() {
        JwtResponse jwtResponse = new JwtResponse("token", 1L, "email@example.com", new Object[]{"ROLE_USER"});
        Object otherObject = new Object();
        assertNotEquals(jwtResponse, otherObject, "Objekte unterschiedlicher Klassen sollten nicht gleich sein");
    }

    /**
     * Testet, ob ein Objekt ungleich null ist
     */
    @Test
    void testEqualsWithNull() {
        JwtResponse jwtResponse = new JwtResponse("token", 1L, "email@example.com", new Object[]{"ROLE_USER"});
        assertNotEquals(null, jwtResponse, "Ein Objekt sollte nicht gleich null sein");
    }

    /**
     * Testet, ob zwei Objekte mit unterschiedlicher Länge des Rollen-Arrays nicht gleich sind
     */
    @Test
    void testEqualsWithDifferentLengthRoles() {
        JwtResponse jwtResponse1 = new JwtResponse("token", 1L, "email@example.com", new Object[]{"ROLE_USER"});
        JwtResponse jwtResponse2 = new JwtResponse("token", 1L, "email@example.com", new Object[]{"ROLE_USER", "ROLE_ADMIN"});
        assertNotEquals(jwtResponse1, jwtResponse2, "Objekte mit unterschiedlicher Länge des Rollen-Arrays sollten nicht gleich sein");
    }


}
