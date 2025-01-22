package com.cityfeedback.backend.mitarbeiterverwaltung.infrastructure;

import com.cityfeedback.backend.benachrichtigungsverwaltung.application.service.BenachrichtigungsService;
import com.cityfeedback.backend.mitarbeiterverwaltung.domain.events.MitarbeiterLoeschen;
import com.cityfeedback.backend.mitarbeiterverwaltung.domain.events.MitarbeiterRegistrieren;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class MitarbeiterEventListener {

    private static final Logger LOG = LoggerFactory.getLogger(com.cityfeedback.backend.mitarbeiterverwaltung.infrastructure.MitarbeiterEventListener.class);

    @Autowired
    BenachrichtigungsService benachrichtigungsService;

    @Value("${app.email.login-link}")
    private String loginLink;


    @TransactionalEventListener(classes = MitarbeiterRegistrieren.class)
    public void mitarbeiterRegistrierenListener(MitarbeiterRegistrieren event) {
        LOG.info("{}: Mitarbeiter-Account für {} {} wurde erfolgreich erstellt und eine Willkommensmail verschickt an: {}", event.getTimestamp(), event.getVorname(), event.getNachname(), event.getEmail());

        // Erstellt die Willkommensmail
        String subject = "Willkommen bei unserem CityFeedback-Portal!";
        String text = "Hallo " + event.getVorname() + " " + event.getNachname() + ",\n" + "Sie haben sich erfolgreich als mitarbeitende Person für das CityFeedback-Portal registriert.\n\n" + "Um eingehende Buerger-Beschwerden zu bearbeiten, melden Sie sich bitte zuerst mit Ihrem Account an:\n" + loginLink + "\n\n" + "Mit freundlichen Grüßen,\n" + "Ihr IT-Team des CityFeedback-Portals";

        // Versendet die E-Mail
        benachrichtigungsService.sendeEmail(event.getEmail(), subject, text);
    }

    @TransactionalEventListener(classes = MitarbeiterLoeschen.class)
    public void mitarbeiterLoeschenListener(MitarbeiterLoeschen event) {
        LOG.info("{}: Mitarbeiter-Account für {} {} wurde erfolgreich geloescht und eine Bestaetigung per Mail verschickt an: {}", event.getTimestamp(), event.getVorname(), event.getNachname(), event.getEmail());

        // Erstellt die Willkommensmail
        String subject = "Bestaetigung: Ihr Mitarbeiter-Konto im CityFeedback-Portal wurde geloescht\n!";
        String text = "Hallo " + event.getVorname() + " " + event.getNachname() + ",\n" + "wir bestaetigen Ihnen, dass Ihr Benutzerkonto für das CityFeedback-Portal erfolgreich geloescht wurde.\n\n" + "Sie können ab sofort keine Buerger-Beschwerden mehr bearbeiten.\n" + "Mit freundlichen Grüßen,\n" + "Ihr IT-Team des CityFeedback-Portals";

        // Versendet die E-Mail
        benachrichtigungsService.sendeEmail(event.getEmail(), subject, text);
    }

}