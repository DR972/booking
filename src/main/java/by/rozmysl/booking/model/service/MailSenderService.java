package by.rozmysl.booking.model.service;

import by.rozmysl.booking.exception.MailException;

/**
 * It  is responsible for sending mail with the <b>config</b> properties.
 */
public interface MailSenderService {

    /**
     * Sends an email message
     *
     * @param emailTo recipient's postal address
     * @param subject message description
     * @param text    message
     * @throws MailException if the message cannot be created
     */
    void sendMail(String emailTo, String subject, String text) throws MailException;

    /**
     * Sends a message and a file by email
     *
     * @param emailTo    recipient's postal address
     * @param subject    fileName
     * @param text       message
     * @param attachment file path
     * @throws MailException if the message cannot be created
     */
    void sendMailWithAttachment(String emailTo, String subject, String text, String attachment) throws MailException;
}
