package basica.server;

/**
 * A class which represents an historical of what the client has bought,
 * how much han spent by buying it.
 *
 * @author Gonzalo Peña Rupérez
 */
public class Value {
    private int share;
    private double cost;

    /**
     * It creates the historical from share and cost.
     *
     * @param share The number of shares that the client has bought.
     * @param cost  The cost that the client has spent by buying shares.
     * @author Gonzalo Peña Rupérez
     */
    public Value(int share, double cost) {
        this.share = share;
        this.cost = share * cost;
    }

    /**
     * It return the number of shares bought.
     *
     * @return number of shares bought.
     */
    public int getShare() {
        return this.share;
    }

    /**
     * It returns the money spent
     *
     * @return the money spent.
     */
    public double getCost() {
        return this.cost;
    }

    /**
     * It updates the number of shares bought.
     *
     * @param share number of shares that the client is going to buy.
     */
    public void setShare(int share) {
        this.share = share;
    }

    /**
     * It updates the money that the client has spent
     *
     * @param cost the money that the client has spent.
     */
    public void setCost(double cost) {
        this.cost = cost;
    }
}
