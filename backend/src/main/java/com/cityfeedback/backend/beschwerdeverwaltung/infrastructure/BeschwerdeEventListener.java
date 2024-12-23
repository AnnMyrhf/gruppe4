package com.cityfeedback.backend.beschwerdeverwaltung.infrastructure;

import com.cityfeedback.backend.beschwerdeverwaltung.application.service.BeschwerdeService;
import com.cityfeedback.backend.beschwerdeverwaltung.domain.events.BeschwerdeAktualisieren;
import com.cityfeedback.backend.beschwerdeverwaltung.domain.events.BeschwerdeErstellen;

import com.cityfeedback.backend.beschwerdeverwaltung.domain.model.Beschwerde;
import com.cityfeedback.backend.beschwerdeverwaltung.infrastructure.BeschwerdeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

    @Component
    public class BeschwerdeEventListener {

        @Autowired
        BeschwerdeService beschwerdeService;

        @Autowired
        BeschwerdeRepository beschwerdeRepository;
        private static final Logger LOG = LoggerFactory.getLogger(BeschwerdeEventListener.class);

        @TransactionalEventListener(classes = BeschwerdeErstellen.class)
        public void beschwerdeErstellenListener(BeschwerdeErstellen event) {
            LOG.info("{}: Beschwerde wurde erfolgreich erstellt: {}", event.getTimestamp(), event.getTitel());
        }

        @TransactionalEventListener(classes = BeschwerdeErstellen.class)
        public void beschwerdeAktualisierenListener(BeschwerdeAktualisieren event) {
            LOG.info("{}: Beschwerde wurde erfolgreich aktualisiert: {} {} {}", event.getTimestamp(), event.getTitel(),event.getStatus(), event.getPrioritaet());
        }

    }

