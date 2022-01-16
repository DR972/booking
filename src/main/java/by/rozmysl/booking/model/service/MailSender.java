package by.rozmysl.booking.model.service;

import by.rozmysl.booking.exception.MailException;

public interface MailSender {

    void sendMail(String emailTo, String subject, String text) throws MailException;

    void sendMailWithAttachment(String emailTo, String subject, String text, String attachment) throws MailException;
}
