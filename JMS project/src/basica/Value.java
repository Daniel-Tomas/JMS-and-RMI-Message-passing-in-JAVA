package basica;

/**
 * A class which represents an historical of what shares the client has bought,
 * how much has spent by buying it and what is the current value of a single share.
 *
 * @author Aaron Cabero Blanco
 */
public class Value {
    private int share;
    private float cost;
    private float currentPrice;

    /**
     * It creates the historical from share and cost.
     *
     * @author Aaron Cabero Blanco
     * @param share The number of shares that the client has bought.
     * @param cost  The cost that the client has spent by buying shares.
     */
    public Value(int share, float cost) {
        this.share = share;
        this.cost = share * cost;
        this.currentPrice = cost;
    }

    /**
     * It returns the number of shares bought.
     *
     * @return number of shares bought.
     */
    public int getShare() {
        return share;
    }

    /**
     * It returns the money spent
     *
     * @return the money spent
     */
    public float getCost() {
        return cost;
    }

    /**
     * It returns actual value of the shares in the market
     *
     * @return actual value
     */
    public float getMarketValue() {
        return currentPrice * share;
    }

    /**
     * It updates the number of shares bought
     *
     * @param share number of shares that the client is going to buy
     */
    public void setShare(int share) {
        this.share = share;
    }

    /**
     * It updates the money that the client has spent
     *
     * @param cost the money that the client has spent
     */
    public void setCost(float cost) {
        this.cost = cost;
    }

    /**
     * It updates the current value of a single share
     *
     * @param currentPrice the actual value of a single share
     */
    public void setCurrentPrice(float currentPrice) {
        this.currentPrice = currentPrice;
    }
}