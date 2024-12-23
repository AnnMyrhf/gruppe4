package com.cityfeedback.backend.email;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@AllArgsConstructor
public class EmailController {

        @Autowired
        private EmailService emailService;

        @GetMapping("/send-email/")
        public String sendEmail(
                @RequestParam String to,
                @RequestParam String subject,
                @RequestParam String text) {
            emailService.sendSimpleEmail(to, subject, text);
            return "Email erfolgreich versendet!";
        }
    }
