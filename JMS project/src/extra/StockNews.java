package extra;

import javax.jms.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

//Recibe la información necesaria, la procesa para mandarla y comunica las noticias de las compañías a los compradores

/**
 * Receives information about how the companies change their quots
 * It processes the information and then it gives the news to the buyers.
 *
 * @author Xiao Peng Ye, Daniel Tomas Sanchez
 */
public class StockNews {
    /**
     * List where all news parsed from text are stored.
     */
    private final LinkedList<News> news;
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

    public StockNews() {
        this.news = new LinkedList<>();
    }

    /**
     * It establish the StockNews connection.
     *
     * @throws FileNotFoundException if stock_news.txt is not found.
     * @throws JMSException          if fails the creation of the connection, session or topic.
     * @author Daniel Tomas Sanchez
     */
    public void startConnection() throws FileNotFoundException, JMSException {
        ConnectionFactory connectionFactory;
        connectionFactory = new com.sun.messaging.ConnectionFactory();
        this.connection = connectionFactory.createConnection();
        this.session = this.connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        this.topic = session.createTopic("News");
        this.storeAllNews();
    }

    /**
     * Stores all news from stock_news.txt in a LinkedList attribute.
     *
     * @throws FileNotFoundException when the file doesn't exist
     * @author Xiao Peng Ye
     */
    private void storeAllNews() throws FileNotFoundException {
        Scanner sc = new Scanner(new File("stock_news.txt"));
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] parts = line.split("-");
            LocalDate date = LocalDate.parse(parts[0].trim(), StockNews.dateFormatter);
            String ticker = parts[1].trim();
            String[] contents = parts[2].split(",");
            this.news.add(new News(ticker, date, contents[1].trim()));
        }
    }

    /**
     * Gets all news of each day each time it is called if there are days remained.
     *
     * @return the news in a day contained in a HashMap filter by ticker or null if all news have already been returned.
     * @author Xiao Peng Ye
     */
    private HashMap<String, ArrayList<News>> getDailyNews() {
        if (this.news.isEmpty())
            return null;
        HashMap<String, ArrayList<News>> dayMapNews = new HashMap<>();
        News oneNews = this.news.peek();
        LocalDate dayDate = oneNews.getDate();

        while (oneNews != null && dayDate.equals(oneNews.getDate())) {
            this.news.poll();
            String ticker = oneNews.getTicker();
            if (!dayMapNews.containsKey(ticker)) {
                dayMapNews.put(ticker, new ArrayList<>());
            }
            dayMapNews.get(ticker).add(oneNews);
            oneNews = this.news.peek();
        }
        return dayMapNews;
    }

    /**
     * Sends daily news contained in a list to subscribers if there are days remained.
     * Otherwise do nothing.
     *
     * @throws JMSException if fails the creation of the message, setting, sending or closing.
     * @return returns true if it has news to send
     * @author Daniel Tomas Sanchez
     */
    public boolean sendDailyNews() throws JMSException {
        HashMap<String, ArrayList<News>> dailyNews = this.getDailyNews();
        ObjectMessage dailyNewsMsg = this.session.createObjectMessage();
        dailyNewsMsg.setObject(dailyNews);
        MessageProducer publisher = this.session.createProducer(this.topic);
        publisher.send(dailyNewsMsg);
        publisher.close();
        return dailyNews != null;
    }

    /**
     * Closes following resources in the given order: publisher, session and connection.
     *
     * @throws JMSException if fails the closing of the session or connection.
     * @author Daniel Tomas Sanchez
     */
    public void closeConnection() throws JMSException {
        this.session.close();
        this.connection.close();
    }
}
