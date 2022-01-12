package by.rozmysl.bookingServlet.model.service;

import by.rozmysl.bookingServlet.exception.MailException;

public interface MailSender {

    void sendMail(String emailTo, String subject, String text) throws MailException;

    void sendMailWithAttachment(String emailTo, String subject, String text, String attachment) throws MailException;
}
