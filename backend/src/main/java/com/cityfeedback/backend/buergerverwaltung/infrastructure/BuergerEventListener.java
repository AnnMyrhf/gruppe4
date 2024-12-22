package com.cityfeedback.backend.buergerverwaltung.infrastructure;

import com.cityfeedback.backend.beschwerdeverwaltung.domain.events.BeschwerdeErstellen;
import com.cityfeedback.backend.buergerverwaltung.domain.events.BuergerRegistrieren;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class BuergerEventListener {

    private static final Logger LOG = LoggerFactory.getLogger(BuergerEventListener.class);

    @TransactionalEventListener(classes = BuergerRegistrieren.class)
    public void buergerRegistrierenListener(BuergerRegistrieren event) {
        LOG.info("{}: Buerger-Account wurde erfolgreich erstellt: {} {}", event.getTimestamp(), event.getName(), event.getEmail());
    }

}

