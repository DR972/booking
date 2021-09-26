package by.rozmysl.bookingServlet.mail;

import by.rozmysl.bookingServlet.entity.user.Booking;
import by.rozmysl.bookingServlet.entity.user.User;

import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.Document;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * Provides a service for creating emails.
 */
public class Letter {

    /**
     * Executes actions to create a message.
     *
     * @param user     User
     * @param language language
     * @return message
     */
    public String createMessage(User user, String language) {
        if (language.equals("en")) {
            return String.format("Hello, %s! \n" + "We are glad to see you on the website of our hotel. To activate " +
                            "your account, please follow the link: http://localhost:8080/anonymous/activation/%s",
                    user.getUsername(), user.getActivationCode());
        } else
            return String.format("Привет, %s! \n" + "Рады Вас видеть на сайте нашей гостиницы. Для активации учетной " +
                            "записи перейдите, пожалуйста, по ссылке: http://localhost:8080/anonymous/activation/%s",
                    user.getUsername(), user.getActivationCode());
    }

    /**
     * Executes actions to create a "Letter" file
     *
     * @param booking booking
     * @param path    file path
     * @throws IOException if the file cannot be created
     */
    public void createInvoice(Booking booking, String path) throws IOException {
        Document doc = new Document(new PdfDocument(new PdfWriter(path)));
        doc.setFont(PdfFontFactory.createFont("src/main/resources/static/myFont.ttf", "Cp1251"));
        doc.add(new Paragraph("Реквизиты отеля\n\n\n"));

        float[] pointColumnWidths = {10F, 100F, 100F, 100F, 80F, 80F};
        Table table = new Table(pointColumnWidths);

        table.addCell(new Cell().add("№"));
        table.addCell(new Cell().add("Услуга"));
        table.addCell(new Cell().add("Характеристика"));
        table.addCell(new Cell().add("Количество"));
        table.addCell(new Cell().add("Цена"));
        table.addCell(new Cell().add("Сумма"));

        table.addCell(new Cell().add("1"));
        table.addCell(new Cell().add("Проживание"));
        table.addCell(new Cell().add(String.valueOf(booking.getRoom().getType())));
        table.addCell(new Cell().add(String.valueOf(booking.getDays())));
        table.addCell(new Cell().add(String.valueOf(booking.getRoom().getPrice())));
        table.addCell(new Cell().add(String.valueOf(booking.getRoom().getPrice().multiply(new BigDecimal(booking.getDays())))));

        table.addCell(new Cell().add("2"));
        table.addCell(new Cell().add("Питание"));
        table.addCell(new Cell().add(String.valueOf(booking.getFood().getType())));
        table.addCell(new Cell().add(String.valueOf(booking.getDays() * booking.getPersons())));
        table.addCell(new Cell().add(String.valueOf(booking.getFood().getPrice())));
        table.addCell(new Cell().add(String.valueOf(booking.getFood().getPrice().multiply(
                new BigDecimal(booking.getDays() * booking.getPersons())))));

        table.addCell(new Cell().add("3"));
        table.addCell(new Cell().add("Дополнительные услуги"));
        table.addCell(new Cell().add(booking.getServices().getType()));
        int quantity = 0;
        if (booking.getServices().getType().equals("стоянка")) quantity = booking.getDays();
        if (booking.getServices().getType().equals("трансфер")) quantity = 1;
        table.addCell(new Cell().add(String.valueOf(quantity)));
        table.addCell(new Cell().add(String.valueOf(booking.getServices().getPrice())));
        table.addCell(new Cell().add(String.valueOf(booking.getServices().getPrice().multiply(new BigDecimal(quantity)))));
        doc.add(table);

        doc.add(new Paragraph("Итого: " + booking.getAmount() + "\n\n\n"));
        doc.add(new Paragraph("Дата заезда: " + booking.getArrival() + "\n" +
                "Дата отъезда: " + booking.getDeparture() + "\n" +
                "Количество дней: " + booking.getDays() + "\n" +
                "Тип номера: " + booking.getRoom().getType() + "\n" +
                "Количество гостей: " + booking.getPersons() + "\n" +
                "Тип питания: " + booking.getFood().getType() + "\n" +
                "Дополнительные услуги: " + booking.getServices().getType() + "\n" +
                "Стоимость: " + booking.getAmount() + "\n"));
        doc.close();
    }

    /**
     * Executes actions to delete the "Letter" file
     *
     * @param path file path
     */
    public void deleteInvoice(String path) {
        File file = new File(path);
        if (file.isFile()) file.delete();
    }
}
