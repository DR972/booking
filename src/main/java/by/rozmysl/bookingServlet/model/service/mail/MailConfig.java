//package by.rozmysl.bookingServlet.model.service.mail;
//
//import java.util.Properties;
//import java.util.StringJoiner;
//
///**
// * Vefines the configuration of the sender's mail with the <b>hotelMail</b>, <b>hotelPassword</b>, <b>props</b> properties
// */
//
//public class MailConfig {
//    private final String hotelMail = "hotelproject111@gmail.com";
//    private final String hotelPassword = "rfirxylirqzomixp";
//    private final Properties props;
//
//    /**
//     * The constructor creates a new object MailConfig
//     */
//    public MailConfig() {
//        props = new Properties();
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.host", "smtp.gmail.com");
//        props.put("mail.smtp.port", "587");
//    }
//
//    /**
//     * Gets the value of the props property
//     *
//     * @return a value of the properties
//     */
//    public Properties getProps() {
//        return props;
//    }
//
//    /**
//     * Gets the value of the hotelMail property
//     *
//     * @return a value of the hotelMail
//     */
//    public String getHotelMail() {
//        return hotelMail;
//    }
//
//    /**
//     * Gets the value of the hotelPassword property
//     *
//     * @return a value of the hotelPassword
//     */
//    public String getHotelPassword() {
//        return hotelPassword;
//    }
//
//    @Override
//    public String toString() {
//        return new StringJoiner(", ", MailConfig.class.getSimpleName() + "[", "]")
//                .add("hotelMail='" + hotelMail + "'")
//                .add("hotelPassword='" + hotelPassword + "'")
//                .add("props=" + props)
//                .toString();
//    }
//}
