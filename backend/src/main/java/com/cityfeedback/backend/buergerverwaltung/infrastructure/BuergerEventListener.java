package com.cityfeedback.backend.buergerverwaltung.infrastructure;

import com.cityfeedback.backend.benachrichtigungsverwaltung.application.service.BenachrichtigungsService;
import com.cityfeedback.backend.buergerverwaltung.domain.events.BuergerLoeschen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class BuergerEventListener {

    private static final Logger LOG = LoggerFactory.getLogger(BuergerEventListener.class);

    @Autowired
    BenachrichtigungsService benachrichtigungsService;

    @TransactionalEventListener(classes = BuergerLoeschen.class)
    public void buergerLoeschenListener(BuergerLoeschen event) {
        LOG.info("{}: Buerger-Account für {} {} wurde erfolgreich geloescht und eine Bestaetigung per Mail verschickt an: {}", event.getTimestamp(), event.getVorname(), event.getNachname(), event.getEmail());

        // Erstellt die Willkommensmail
        String subject = "Bestaetigung: Ihr Konto im CityFeedback-Portal wurde geloescht\n!";
        String text = "Hallo " + event.getVorname() + " " + event.getNachname() + ",\n" +
                "wir bestaetigen Ihnen, dass Ihr Benutzerkonto auf unserem CityFeedback-Portal erfolgreich geloescht wurde.\n\n" +
                "Alle Ihre Daten wurden gemaess unserer Datenschutzbestimmungen unwiderruflich entfernt. Sie erhalten ab sofort keine Benachrichtigungen mehr von uns.\n" +
                "Mit freundlichen Grüßen,\n" +
                "Ihr CityFeedback-Team";

        // Versendet die E-Mail
        benachrichtigungsService.sendeEmail(event.getEmail(), subject, text);
    }

}

