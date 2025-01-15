package com.cityfeedback.backend.beschwerdeverwaltung.infrastructure;

import com.cityfeedback.backend.benachrichtigungsverwaltung.application.service.BenachrichtigungsService;
import com.cityfeedback.backend.beschwerdeverwaltung.domain.events.BeschwerdeAktualisieren;
import com.cityfeedback.backend.beschwerdeverwaltung.domain.events.BeschwerdeErstellen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

    @Component
    public class BeschwerdeEventListener {

        @Autowired
        BenachrichtigungsService benachrichtigungsService;

        @Autowired
        BeschwerdeRepository beschwerdeRepository;
        private static final Logger LOG = LoggerFactory.getLogger(BeschwerdeEventListener.class);

        @TransactionalEventListener(classes = BeschwerdeErstellen.class)
        public void beschwerdeErstellenListener(BeschwerdeErstellen event) {
            LOG.info("{}: Beschwerde wurde erfolgreich erstellt: {} und eine Bestätigung an folgende Mail-Adresse versendet:", event.getTimestamp(), event.getTitel(), event.getEmail());

            // Erstellt die Bestaetigungsmail
            String betreff = "CityFeedback-Portal: Ihre Beschwerde ist bei uns eingegangen";
            String text = "Vielen Dank für die Nutzung unseres CityFeedback-Portals!\n\n" +
                    "Mit dieser E-Mail bestätigen wir Ihnen den Eingang Ihrer Beschwerde:\n\n" + event.getTitel() + " um " + event.getTimestamp() + "\n\n" +  "Die Bearbeitung Ihrer Beschwerde kann einige Zeit in Anspruch nehmen. Wir bitten Sie daher um etwas Geduld. Den Bearbeitungsstatus Ihrer Beschwerde(n) können Sie sich in Ihrem Buerger-Dashboard ansehen\n\n" +
                    "Mit freundlichen Grüßen\n" +
                    "Ihr CityFeedback-Team";

            // Versendet die E-Mail
            benachrichtigungsService.sendeEmail(event.getEmail(), betreff, text);
        }

        @TransactionalEventListener(classes = BeschwerdeAktualisieren.class)
        public void beschwerdeAktualisierenListener(BeschwerdeAktualisieren event) {
            LOG.info("{}: Beschwerde wurde erfolgreich aktualisiert: {} {} {}", event.getTimestamp(), event.getTitel(),event.getStatus(), event.getPrioritaet());
        }

    }

