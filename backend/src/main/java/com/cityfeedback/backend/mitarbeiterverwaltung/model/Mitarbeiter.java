package com.cityfeedback.backend.mitarbeiterverwaltung.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY) // id wird automatisch inkrementiert
    private Long id;

    @NotBlank(message = "Anrede darf nicht leer sein!")//  darf nicht null oder leer sein
    private String anrede;

    @NotBlank(message = "Vorname darf nicht leer sein!")
    @Size(max = 30, message = "Vorname darf max. 30 Zeichen lang sein!")
    private String vorname;

    @NotBlank(message = "Nachname darf nicht leer sein!")
    @Size(max = 30, message = "Nachname darf max. 30 Zeichen lang sein!")
    private String nachname;

    @Pattern(regexp = "^[+]?\\d+(-\\d+)*$", message = "Die Telefonnummer darf nur Zahlen, Bindestriche und ein '+' enthalten.")
    @NotBlank(message = "Telefonnummer darf nicht leer sein!")
    private String telefonnummer;

    @Email
    @NotBlank(message = "E-Mail darf nicht leer sein!")
    private String email;

    @NotBlank(message = "Passwort darf nicht leer sein!")
    private String passwort;

    @NotBlank(message = "Abteilung darf nicht leer sein!")
    private String abteilung = "Bürgerservice";

    @NotBlank(message = "Position darf nicht leer sein!")
    private String position = "Angestellter";

    public Mitarbeiter(String anrede, String vorname, String nachname, String telefonnummer, String email, String passwort) {
        this.anrede = anrede;
        this.vorname = vorname;
        this.nachname = nachname;
        this.telefonnummer = telefonnummer;
        this.email = email;
        this.passwort = passwort;
    }
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

    @Override
    public String toString() {
        return "Mitarbeiter(" +
                "anrede='"+ anrede + '\'' +
                ", vorname='" + vorname + '\'' +
                ", nachname='" + nachname + '\'' +
                ", telefonnummer='" + telefonnummer + '\'' +
                ", email='" + email + '\'' +
                ", passwort='" + passwort + '\'' +
                ", abteilung='" + abteilung + '\'' +
                ", position='" + position + '\'' +
                ')';
    }
}

