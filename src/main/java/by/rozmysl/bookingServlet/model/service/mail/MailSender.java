package by.rozmysl.bookingServlet.model.service.mail;

import by.rozmysl.bookingServlet.exception.MailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * The class is responsible for sending mail with the <b>config</b> properties.
 */
public class MailSender {
    private static final Logger LOGGER = LoggerFactory.getLogger(MailSender.class);
    private static final String MAIL_PATH = "mail/mail.properties";
    private static final String MAIL_NAME = "mail.name";
    private static final String MAIL_PASSWORD = "mail.password";

    private static Session session;
    private static final Properties properties = new Properties();
    private static MailSender instance;

    private MailSender() throws MailException {
        try (InputStream inputStream = MailSender.class.getClassLoader().getResourceAsStream(MAIL_PATH)) {
            properties.load(inputStream);
            System.out.println(properties);
            session = Session.getInstance(properties, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(MAIL_NAME, MAIL_PASSWORD);
                }
            });
        } catch (IOException e) {
            LOGGER.error("Can not load property data to mail sender ", e);
            throw new MailException("Can't load properties file: ", e);
        }
    }

    public static MailSender getInstance() throws MailException {
        if (instance == null) {
            instance = new MailSender();
        }
        return instance;
    }


    /**
     * Sends an email message
     *
     * @param emailTo recipient's postal address
     * @param subject message description
     * @param text    message
     * @throws MailException if the message cannot be created
     */
    public void sendMail(String emailTo, String subject, String text) throws MailException {
        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(MAIL_NAME));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
//            message.setFrom(new InternetAddress(MAIL_NAME));
//            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo));
            message.setSubject(subject);
            message.setText(text);
            Transport.send(message);
        } catch (MessagingException e) {
            LOGGER.error("Send message with Activation code has been failed", e);
            throw new MailException("Send message with Activation code has been failed", e);
        }
    }

    /**
     * Sends a message and a file by email
     *
     * @param emailTo    recipient's postal address
     * @param subject    fileName
     * @param text       message
     * @param attachment file path
     * @throws MailException if the message cannot be created
     */
    public void sendMailWithAttachment(String emailTo, String subject, String text, String attachment) throws MailException {
        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(MAIL_NAME));
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
        } catch (MessagingException e) {
            LOGGER.error("Send invoice has been failed", e);
            throw new MailException("Send invoice has been failed", e);
        }
    }
}
