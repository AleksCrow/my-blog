package com.voronkov.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSender {

  @Autowired
  private JavaMailSender mailSender;

  public void send(String emailTo, String subject, String message) {
    SimpleMailMessage mailMessage = new SimpleMailMessage();

    mailMessage.setFrom("Qwerton00@gmail.com");
    mailMessage.setTo(emailTo);
    mailMessage.setSubject(subject);
    mailMessage.setText(message);

    mailSender.send(mailMessage);
  }

}
