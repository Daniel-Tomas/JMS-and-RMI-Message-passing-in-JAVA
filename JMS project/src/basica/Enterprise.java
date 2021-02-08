package basica;

import java.io.Serializable;

/**
 * A class to represent a company registered in the investment bank with
 * the information of its ticker, name and the sector it belongs to.
 *
 * @author Xiao Peng Ye
 */
public class Enterprise implements Serializable {
    private final String ticker;
    private final String name;
    private final String sector;

    /**
     * Instantiates a new basica.Enterprise.
     *
     * @param ticker the ticker
     * @param name   the name
     * @param sector the sector
     */
    public Enterprise(String ticker, String name, String sector) {
        this.ticker = ticker;
        this.name = name;
        this.sector = sector;
    }

    /**
     * Gets sector.
     *
     * @return the sector
     */
    public String getSector() {
        return sector;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets ticker.
     *
     * @return the ticker
     */
    public String getTicker() {
        return ticker;
    }
}
