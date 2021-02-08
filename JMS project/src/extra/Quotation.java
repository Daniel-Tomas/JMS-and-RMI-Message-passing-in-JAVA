package extra;

import java.io.Serializable;
import java.time.LocalDate;


/**
 * A class for represents the information of a quote read from the text and contains the necessary
 * information of the companies so that the client can make a decision
 *
 * @author Xiao Peng Ye, Daniel Tomas Sanchez
 * @version V1.0, 25/10/2020
 */
public class Quotation implements Serializable {
    private final Enterprise enterprise;
    private final LocalDate date;
    private final float cost;
    private final float exchange;

    /**
     * Instantiates a new Quotation with all of his attributes set
     *
     * @param enterprise the enterprise
     * @param date       the date
     * @param cost       the cost
     * @param exchange   the exchange
     */
    public Quotation(Enterprise enterprise, LocalDate date, float cost, float exchange) {
        this.enterprise = enterprise;
        this.date = date;
        this.cost = cost;
        this.exchange = exchange;
    }

    /**
     * Gets enterprise.
     *
     * @return the enterprise
     */
    public Enterprise getEnterprise() {
        return enterprise;
    }

    /**
     * Gets date.
     *
     * @return the date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Gets cost.
     *
     * @return the cost
     */
    public float getCost() {
        return cost;
    }

    /**
     * Gets exchange.
     *
     * @return the exchange
     */
    public float getExchange() {
        return exchange;
    }
}
