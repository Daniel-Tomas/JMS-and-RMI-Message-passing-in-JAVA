package basica;

import javax.jms.*;
import java.util.HashMap;

/**
 * A class which represents a client. It's an abstract class because there will be some different clients
 * who will have different interest in buying actions. There are 2 methods that will have every single client.
 * This methods are buyShare and printWallet. The other one method is abstract because each client will have
 * a different interest of share, so it will be implemented in the different classes of clients.
 *
 * @author Aaron Cabero Blanco, Gonzalo Peña Rupérez, Xiao Peng Ye
 */
public abstract class Client implements MessageListener {
    /**
     * Set of shares purchased by the client
     */
    protected final HashMap<String, Value> wallet;
    /**
     * Connection used in publish/subscribe model.
     */
    protected Connection connection;
    /**
     * Session used in publish/subscribe model.
     */
    protected Session session;
    /**
     * MessageConsumer used in publish/subscribe model.
     */
    protected MessageConsumer subscriber;
    /**
     * Topic used in publish/subscribe model.
     */
    protected Topic topic;
    /**
     * Condition to verify the connection.
     */
    protected boolean finished;

    /**
     * Constructs a basica.Client.
     *
     * @author Xiao Peng Ye
     */
    public Client() {
        this.wallet = new HashMap<>();
        this.finished = false;
    }

    /**
     * It makes the action of a purchase on behalf of the client.
     *
     * @param ticker The identifying of the shares enterprise.
     * @param share  The number of shares the client is going to buy.
     * @param price  The price of a single share.
     * @author Aarón Cabero Blanco
     */
    protected void buyShare(String ticker, int share, float price) {
        if (!wallet.containsKey(ticker)) {
            wallet.put(ticker, new Value(share, price));
        } else {
            Value value = wallet.get(ticker);
            value.setShare(value.getShare() + share);
            value.setCost(value.getCost() + share * price);
            value.setCurrentPrice(price);
        }
    }


    /**
     * Updates the current price of the share.
     *
     * @param ticker The identifying of the shares enterprise.
     * @param cost   The current price of the share
     * @author Aarón Cabero Blanco
     */
    protected void updatePrice(String ticker, float cost) {
        if (wallet.containsKey(ticker)) {
            Value value = wallet.get(ticker);
            value.setCurrentPrice(cost);
        }

    }

    /**
     * Prints a summary of the client's wallet.
     *
     * @author Gonzalo Peña Rupérez
     */

    public void printInvestment() {
        System.out.format("%-20s%-20s%-20s%-20s\n", "Ticker", "#Acciones", "Coste", "Valor");
        for (HashMap.Entry<String, Value> entry : wallet.entrySet()) {
            System.out.format("%-20s%-20d%-20.2f%-20.2f\n", entry.getKey(), entry.getValue().getShare(), entry.getValue().getCost(), entry.getValue().getMarketValue());
        }
    }

    /**
     * It establish the Client conection
     *
     * @throws JMSException if something fail due to creation of connection, session, topic or consumer.
     * @author Xiao Peng Ye, Sergio Sánchez-Carvajales Francoy, Juan Diego Valencia Marin
     */

    public void startConnection() throws JMSException {
        ConnectionFactory connectionFactory;

        connectionFactory = new com.sun.messaging.ConnectionFactory();
        this.connection = connectionFactory.createConnection();
        this.session = this.connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //topic = this.session.createTopic("Stock");
        this.topic = session.createTopic("Stock");
        this.subscriber = session.createConsumer(topic);
        this.subscriber.setMessageListener(this);
        this.connection.start();
    }

    /**
     * Closes following resources in the given order: subscriber, session and connection.
     *
     * @throws JMSException if fails the closing of the subscriber, session or connection.
     * @author Xiao Peng Ye
     */
    public void closeConnection() throws JMSException {
        this.subscriber.close();
        this.session.close();
        this.connection.close();
    }

    protected void finish() {
        this.finished = true;
    }

    public boolean isFinished() {
        return this.finished;
    }
}
