package basica;

import javax.jms.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


/**
 * Represents communication between customers and the investment bank.
 * Receives such information and processes it to send with publish/subscribe model.
 *
 * @author Juan Diego Valencia Marin, Daniel Tomas Sanchez
 * @version V1.0, 25/10/2020
 */
public class StockBroker {
    /**
     * Hash table where all enterprises are registered.
     */
    private final Map<String, Enterprise> enterprises;
    /**
     * List where all quotations parsed from text are stored.
     */
    private final LinkedList<Quotation> quotations;
    /**
     * Formatter used to parse date string into a LocalTime object.
     */
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    /**
     * Connection used in publish/subscribe model.
     */
    private Connection connection;
    /**
     * Session used in publish/subscribe model.
     */
    private Session session;
    /**
     * Topic used in publish/subscribe model.
     */
    private Topic topic;

    /**
     * Costructs a basica.StockBroker containing all quotations from stock_prices.txt and initializes resources for connection.
     *
     * @author Juan Diego Valencia Marin, Daniel Tomas Sanchez
     */
    public StockBroker() {
        this.enterprises = new HashMap<>();
        this.quotations = new LinkedList<>();
    }

    /**
     * It establish the StockBroker connection.
     *
     * @author Daniel Tomas Sanchez, Juan Diego Valencia Marin
     * @throws FileNotFoundException if stock_prices.txt is not found.
     * @throws JMSException if fails the creation of the connection, session or topic.
     */
    public void startConnection() throws FileNotFoundException, JMSException {
        ConnectionFactory connectionFactory;

        connectionFactory = new com.sun.messaging.ConnectionFactory();

        this.connection = connectionFactory.createConnection();

        this.session = this.connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        this.topic = session.createTopic("Stock");

        this.storeAllQuotations();
    }

    /**
     * Gets enterprise.
     *
     * @param ticker the ticker
     * @return the enterprise
     * @author Juan Diego Valencia Marin
     */
    private Enterprise getEnterprise(String ticker) {
        return this.enterprises.get(ticker);
    }

    /**
     * Register enterprise.
     *
     * @param ticker     the ticker
     * @param enterprise the enterprise
     * @author Juan Diego Valencia Marin
     */
    public void registerEnterprise(String ticker, Enterprise enterprise) {
        this.enterprises.put(ticker, enterprise);
    }

    /**
     * Stores all quotations from stock_prices.txt in a LinkedList attribute.
     *
     * @throws FileNotFoundException if fails when the file can not be found.
     * @author Daniel Tomas Sanchez
     */
    private void storeAllQuotations() throws FileNotFoundException {
        Scanner sc = new Scanner(new File("stock_prices.txt"));

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] parts = line.split(" ");

            LocalDate date = LocalDate.parse(parts[0], StockBroker.dateFormatter);
            Enterprise enterprise = this.getEnterprise(parts[1]);
            float cost = Float.parseFloat(parts[2]);
            float exchange = Float.parseFloat(parts[3]);

            this.quotations.add(new Quotation(enterprise, date, cost, exchange));
        }
    }

    /**
     * Gets all quotations of each day each time it is called if there are days remained.
     *
     * @return the quotations in a day contained in a list or null if all quotations have already been returned.
     * @author Daniel Tomas Sanchez
     */
    private ArrayList<Quotation> getDayQuotations() {
        if (this.quotations.isEmpty())
            return null;

        ArrayList<Quotation> dayQuotations = new ArrayList<>();
        Quotation oneQuotation = this.quotations.peek();
        LocalDate dayDate = oneQuotation.getDate();

        while (oneQuotation != null && dayDate.equals(oneQuotation.getDate())) {
            this.quotations.poll();
            dayQuotations.add(oneQuotation);
            oneQuotation = this.quotations.peek();
        }
        return dayQuotations;
    }

    /**
     * Sends daily quotations contained in a list to subscribers if there are days remained.
     * Otherwise do nothing.
     *
     * @author Daniel Tomas Sanchez, Juan Diego Valencia Marin
     * @throws JMSException if fails the creation of the message, setting, sending or closing.
     * @return returns true if it has quations to send
     */
    public boolean sendDailyQuotations() throws JMSException {
        ArrayList<Quotation> dailyQuotations = this.getDayQuotations();
        ObjectMessage dailyQuotationsMsg = this.session.createObjectMessage();
        dailyQuotationsMsg.setObject(dailyQuotations);
        MessageProducer publisher = this.session.createProducer(this.topic);
        publisher.send(dailyQuotationsMsg);
        publisher.close();
        return dailyQuotations != null;
    }

    /**
     * Closes following resources in the given order: publisher, session and connection.
     *
     * @author Daniel Tomas Sanchez, Juan Diego Valencia Marin
     * @throws JMSException if fails the closing of the session or connection.
     */
    public void closeConnection() throws JMSException {
        this.session.close();
        this.connection.close();
    }

    public static void main(String[] args) {
        StockBroker stock = new StockBroker();

        stock.registerEnterprise("AAPL", new Enterprise("AAPL", "Apple Inc", "Technology"));
        stock.registerEnterprise("GOOGL", new Enterprise("GOOGL", "Alphabet Inc", "Technology"));
        stock.registerEnterprise("TEVA", new Enterprise("TEVA", "Teva Pharmaceutical Industries Ltd.", "Health"));
        stock.registerEnterprise("RO", new Enterprise("RO", "Roche Holding AG", "Health"));
        stock.registerEnterprise("REP", new Enterprise("REP", "Repsol SA", "Energy"));
        stock.registerEnterprise("GALP", new Enterprise("GALP", "Galp Energia SGPS S/A", "Energy"));

        try {
            stock.startConnection();
        } catch (FileNotFoundException e) {
            System.out.println("Error al abrir el archivo stock_prices.txt");
            return;
        } catch (JMSException jmse) {
            System.out.println("Error al establecer la conexión");
            return;
        }

        try {
            while (stock.sendDailyQuotations()) ;
        } catch (JMSException jmse) {
            System.out.println("Error al mandar las acciones");
            return;
        }

        try {
            stock.closeConnection();
        } catch (JMSException jmse) {
            System.out.println("Error al cerrar la conexión");
        }
    }


}





