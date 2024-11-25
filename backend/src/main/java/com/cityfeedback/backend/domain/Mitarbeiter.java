package com.cityfeedback.backend.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Repraesentiert einen Buerger in der CityFeedback-Anwendung
 */
@Entity // JPA-Entitaet fuer DB
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mitarbeiter implements UserDetails {

    @Id // Markiert id als Primaerschluessel
    @SequenceGenerator(
            name = "mitarbeiter_id",
            sequenceName = "mitarbeiter_id",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "mitarbeiter_id"
    )
    private Long id;

    private String anrede;
    private String vorname;
    private String nachname;
    private String telefonnummer;
    private String email;
    private String passwort;
    private String abteilung;
    private String position;

    /*
     * Basisimplementierung (SimpleGrantedAuthority) für Zugriffskontrollentscheidung
     *
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("MITARBEITER"));
    }

    @Override
    public String getPassword() {
        return passwort;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
