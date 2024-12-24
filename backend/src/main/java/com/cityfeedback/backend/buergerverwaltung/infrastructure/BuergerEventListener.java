package com.cityfeedback.backend.buergerverwaltung.infrastructure;

import com.cityfeedback.backend.benachrichtigungsverwaltung.application.service.BenachrichtigungsService;
import com.cityfeedback.backend.buergerverwaltung.domain.events.BuergerRegistrieren;
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

    @Value("${app.email.login-link}")
    private String loginLink;


    @TransactionalEventListener(classes = BuergerRegistrieren.class)
    public void buergerRegistrierenListener(BuergerRegistrieren event) {
        LOG.info("{}: Buerger-Account für {} {} wurde erfolgreich erstellt und eine Willkommensmail verschickt an: {}", event.getTimestamp(), event.getVorname(), event.getNachname(), event.getEmail());

        // Erstellt die Willkommensmail
        String subject = "Willkommen bei unserem CityFeedback-Portal!";
        String text = "Hallo " + event.getVorname() + " " + event.getNachname() + ",\n" +
                "wir freuen uns, dass Sie sich bei unserem CityFeedback-Portal registriert haben.\n\n" +
                "Um eine Beschwerde zu erstellen, melden Sie sich bitte zuerst mit Ihrem Account an:\n" +
                loginLink + "\n\n" +
                "Mit freundlichen Grüßen,\n" +
                "Ihr CityFeedback-Team";

        // Versendet die E-Mail
        benachrichtigungsService.sendeEmail(event.getEmail(), subject, text);
    }

}

