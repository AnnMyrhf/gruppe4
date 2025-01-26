package com.cityfeedback.backend.benachrichtigungsverwaltung.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 *  Service zum Versenden von Benachrichtigungen basierend auf DomainEvents
 */
@Service
public class BenachrichtigungsService {

    @Autowired
    private JavaMailSender mailSender;

        public void sendeEmail(String empfaenger, String betreff, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(empfaenger);
        message.setSubject(betreff);
        message.setText(text);
        message.setFrom("cityfeedback@gruppe4.de");
        mailSender.send(message);
    }
}

