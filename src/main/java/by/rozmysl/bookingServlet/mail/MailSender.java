package by.rozmysl.bookingServlet.mail;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

/**
 * The class is responsible for sending mail with the <b>config</b> properties.
 */
public class MailSender {
    private final MailConfig config = new MailConfig();

    /**
     * Sends an email message
     *
     * @param emailTo recipient's postal address
     * @param subject message description
     * @param text    message
     * @throws MessagingException if the message cannot be created
     */
    public void sendMail(String emailTo, String subject, String text) throws MessagingException {
        Session session = Session.getInstance(config.getProps(), new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(config.getHotelMail(), config.getHotelPassword());
            }
        });
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(config.getHotelMail()));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo));
        message.setSubject(subject);
        message.setText(text);
        Transport.send(message);
    }

    /**
     * Sends a message and a file by email
     *
     * @param emailTo    recipient's postal address
     * @param subject    fileName
     * @param text       message
     * @param attachment file path
     * @throws MessagingException if the message cannot be created
     */
    public void sendMailWithAttachment(String emailTo, String subject, String text, String attachment) throws MessagingException {
        Session session = Session.getInstance(config.getProps(), new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(config.getHotelMail(), config.getHotelPassword());
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(config.getHotelMail()));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
        message.setSubject(subject);

        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText(text);
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        messageBodyPart = new MimeBodyPart();
        DataSource source = new FileDataSource(attachment);
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(attachment);
        multipart.addBodyPart(messageBodyPart);
        message.setContent(multipart);

        Transport.send(message);
    }
}
