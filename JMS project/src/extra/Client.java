package extra;

import javax.jms.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * A class which represents a client. It's an abstract class because there will be some different clients
 * who will have different interest in buying actions. There are 2 methods that will have every single client.
 * This methods are buyShare and printWallet. The other one method is abstract because each client will have
 * a different interest of share, so it will be implemented in the different classes of clients.
 *
 * @author Aaron Cabero Blanco, Gonzalo Peña Rupérez, Xiao Peng Ye, Daniel Tomas Sanchez
 */
public abstract class Client implements MessageListener {
    /**
     * Set of shares purchased by the client
     */
    protected final HashMap<String, Value> wallet;
    protected ArrayList<Quotation> quotations;
    protected HashMap<String, ArrayList<News>> news;
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
    protected MessageConsumer subscriberPrice;
    /**
     * MessageConsumer used in publish/subscribe model.
     */
    protected MessageConsumer subscriberNews;
    /**
     * Topic used in publish/subscribe model.
     */
    protected Topic topic;
    /**
     * Condition to verify when connection ends and can be closed.
     */
    protected boolean finished;
    /**
     * Links news content with the opinions about them
     */
    private final Map<String, Boolean> newsOpinion;

    /**
     * Action to decide to buy a share or not
     */
    abstract protected void makePurchase();

    /**
     * Constructs a extra.Client and fills newsOpinions.
     *
     * @author Xiao Peng Ye, Daniel Tomas Sanchez
     */
    public Client() {
        this.wallet = new HashMap<>();
        this.finished = false;
        this.newsOpinion = new Hashtable<>();

        this.newsOpinion.put("ha experimentado un aumento en las ventas", true);
        this.newsOpinion.put("ha reportado un aumento en los beneficios de la compañía", true);
        this.newsOpinion.put("ha abierto una nueva fábrica para aumentar la producción de la compañía", true);
        this.newsOpinion.put("ha reducido sensiblemente la deuda de la compañía", true);

        this.newsOpinion.put("ha sufrido un decremento en las ventas", false);
        this.newsOpinion.put("ha reportado una disminución en los beneficios de la compañía", false);
        this.newsOpinion.put("ha recibido una fuerte sanción económica del organismo regulador", false);
        this.newsOpinion.put("está alcanzando niveles preocupantes de deuda", false);

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
     * @param cost   The current price of the share.
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
     * It establish the Client connection
     *
     * @throws JMSException if something fail due to creation of connection, session, topic or consumer.
     * @author Xiao Peng Ye, Sergio Sánchez-Carvajales Francoy, Juan Diego Valencia Marin
     */
    public void startConnection() throws JMSException {
        ConnectionFactory connectionFactory;

        connectionFactory = new com.sun.messaging.ConnectionFactory();
        this.connection = connectionFactory.createConnection();
        this.session = this.connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        this.topic = session.createTopic("Stock");
        this.subscriberPrice = session.createConsumer(topic);
        this.subscriberPrice.setMessageListener(this);
        this.topic = session.createTopic("News");
        this.subscriberNews = session.createConsumer(topic);
        this.subscriberNews.setMessageListener(this);
        this.connection.start();
    }

    /**
     * Closes following resources in the given order: subscriber, session and connection.
     *
     * @throws JMSException if fails the closing of the subscriber, session or connection.
     * @author Xiao Peng Ye
     */
    public void closeConnection() throws JMSException {
        this.subscriberPrice.close();
        this.subscriberNews.close();
        this.session.close();
        this.connection.close();
    }


    /**
     * Marks the end of the connection.
     *
     * @author Daniel Tomas Sanchez
     */
    protected void finish() {
        this.finished = true;
    }

    /**
     * Checks if the connection has ended in order to be closed.
     *
     * @return the condition.
     * @author Daniel Tomas Sanchez
     */
    public boolean isFinished() {
        return this.finished;
    }

    /**
     * Gets opinion about news content.
     *
     * @param newsContent the news content.
     * @return true if the opinion is positive, false otherwise.
     * @author Daniel Tomas Sanchez
     */
    protected boolean getOpinion(String newsContent) {
        return this.newsOpinion.get(newsContent);
    }
}
