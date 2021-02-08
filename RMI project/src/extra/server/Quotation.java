package extra.server;

import java.io.Serializable;
import java.time.LocalDate;


/**
 * A class for represents the information of a quote read from the text and contains the necessary
 * information of the companies so that the client can make a decision
 *
 * @author Xiao Peng Ye, Daniel Tomas Sanchez
 */
public class Quotation implements Serializable {
    private final LocalDate date;
    private final float cost;
    private final float exchange;
    private final String ticker;
    private final String sector;

    /**
     * Instantiates a new Quotation with all of his attributes set
     *
     * @param ticker     the enterprise ticker
     * @param sector     the enterprise sector
     * @param date       the date
     * @param cost       the cost
     * @param exchange   the exchange
     */
    public Quotation(String ticker, String sector, LocalDate date, float cost, float exchange) {
        this.ticker = ticker;
        this.sector = sector;
        this.date = date;
        this.cost = cost;
        this.exchange = exchange;
    }

    /**
     * Gets enterprise ticker
     *
     * @return the ticker
     */
    public String getTicker() {
        return this.ticker;
    }

    /**
     * Gets enterprise sector
     *
     * @return the sector
     */
    public String getSector() {
        return this.sector;
    }

    /**
     * Gets date.
     *
     * @return the date
     */
    public LocalDate getDate() {
        return this.date;
    }

    /**
     * Gets cost.
     *
     * @return the cost
     */
    public float getCost() {
        return this.cost;
    }

    /**
     * Gets exchange.
     *
     * @return the exchange
     */
    public float getExchange() {
        return this.exchange;
    }
}
