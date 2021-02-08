package extra.server;


import java.io.Serializable;
import java.time.LocalDate;

/**
 * A class for represents the information of a news read from the text
 *
 * @author Xiao Peng Ye
 * @version V1.0, 7/11/2020
 */
public class News implements Serializable {
    private final String ticker;
    private final LocalDate date;
    private final String content;

    /**
     * Instantiates a new News with all of his attributes set
     *
     * @param ticker  the company ticker
     * @param date    the date
     * @param content the news content
     */
    public News(String ticker, LocalDate date, String content) {
        this.ticker = ticker;
        this.date = date;
        this.content = content;
    }

    /**
     * Gets company ticker
     *
     * @return the ticker
     */
    public String getTicker() {
        return ticker;
    }

    /**
     * Gets news date
     *
     * @return the date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Gets news content
     *
     * @return the content
     */
    public String getContent() {
        return content;
    }
}
