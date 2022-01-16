package by.rozmysl.booking.controller.tag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Provides service to format money in jsp with the <b>value</b> properties.
 */
public class MoneyTag extends TagSupport {
    private static final Logger LOGGER = LoggerFactory.getLogger(MoneyTag.class);
    private static final String BE = "be";
    private static final String BY = "BY";
    private double value;

    /**
     * The method sets the value of the value property
     *
     * @param value value
     */
    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public int doStartTag() throws JspException {
        NumberFormat rubleFormat = NumberFormat.getCurrencyInstance(new Locale(BE, BY));
        try {
            this.pageContext.getOut().print(rubleFormat.format(value));
        } catch (IOException e) {
            LOGGER.error(String.valueOf(e));
            throw new JspException(e);
        }
        return SKIP_BODY;
    }
}