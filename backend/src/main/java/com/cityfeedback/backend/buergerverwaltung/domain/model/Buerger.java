package com.cityfeedback.backend.buergerverwaltung.domain.model;

import com.cityfeedback.backend.beschwerdeverwaltung.domain.model.Beschwerde;
import com.cityfeedback.backend.buergerverwaltung.domain.events.DomainEvent;
import com.cityfeedback.backend.buergerverwaltung.domain.events.BuergerLoeschen;
import com.cityfeedback.backend.buergerverwaltung.domain.events.BuergerRegistrieren;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.DomainEvents;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.cityfeedback.backend.beschwerdeverwaltung.domain.valueobjects.Status.EINGEGANGEN;

/**
 * Repraesentiert einen Buerger in der CityFeedback-Anwendung
 */
@Entity // JPA-Entitaet fuer DB
@Data // automatisch Getter, Setter, toString() usw
@NoArgsConstructor // leerer Konstruktor fuer JPA benoetigt
@AllArgsConstructor // Konstruktor mit allen Attributen
public class Buerger implements UserDetails {

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

    //@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$", message = "Das Passwort min. 8 Zeichen lang sein und mindestens einen Buchstaben, eine Zahl und ein Sonderzeichen enthalten!")
    @NotBlank(message = "Passwort darf nicht leer sein!")
    private String passwort;

    @OneToMany(mappedBy = "buerger", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    // Verhindert Endlosschleifen und stellt sicher, dass die Beschwerden in JSON zurückgegeben werden
    private List<Beschwerde> beschwerden;

    public Buerger(String anrede, String vorname, String nachname, String telefonnummer, String email, String passwort, List<Beschwerde> beschwerden) {
        this.anrede = anrede;
        this.vorname = vorname;
        this.nachname = nachname;
        this.telefonnummer = telefonnummer;
        this.email = email;
        this.passwort = passwort;
        this.beschwerden = new ArrayList<>(); // Initialisiere die Liste der Beschwerden
    }

    @DomainEvents
    public List<DomainEvent> getDomainEvents() {
        List<DomainEvent> events = new ArrayList<>();

        if (getId() == null) {
            events.add(new BuergerRegistrieren(this));
        } else {
            events.add(new BuergerLoeschen(this));
        }

        return events;
    }

    /*
     * Basisimplementierung (SimpleGrantedAuthority) für Zugriffskontrollentscheidung
     *
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("BUERGER"));
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
        return "Buerger(" +
                "anrede='" + anrede + '\'' +
                ", vorname='" + vorname + '\'' +
                ", nachname='" + nachname + '\'' +
                ", telefonnummer='" + telefonnummer + '\'' +
                ", email='" + email + '\'' +
                ", passwort='" + passwort + '\'' +
                ", beschwerden=" + beschwerden +
                ')';
    }
}

