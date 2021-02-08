package extra;

import javax.jms.JMSException;
import java.io.FileNotFoundException;

/**
 * Main to run StockBroker and StockNews
 *
 * @author Xiao Peng Ye
 */
public class Stocks {
    public static void main(String[] args) {
        StockBroker stockBroker = new StockBroker();
        StockNews stockNews = new StockNews();
        stockBroker.registerEnterprise("AAPL", new Enterprise("AAPL", "Apple Inc", "Technology"));
        stockBroker.registerEnterprise("GOOGL", new Enterprise("GOOGL", "Alphabet Inc", "Technology"));
        stockBroker.registerEnterprise("TEVA", new Enterprise("TEVA", "Teva Pharmaceutical Industries Ltd.", "Health"));
        stockBroker.registerEnterprise("RO", new Enterprise("RO", "Roche Holding AG", "Health"));
        stockBroker.registerEnterprise("REP", new Enterprise("REP", "Repsol SA", "Energy"));
        stockBroker.registerEnterprise("GALP", new Enterprise("GALP", "Galp Energia SGPS S/A", "Energy"));

        try {
            stockBroker.startConnection();
            stockNews.startConnection();
        } catch (FileNotFoundException e) {
            System.out.println("Error al abrir el archivo stock_news.txt");
            return;
        } catch (JMSException jmse) {
            System.out.println("Error al establecer la conexión");
            return;
        }

        try {
            while (stockBroker.sendDailyQuotations() && stockNews.sendDailyNews()) ;
        } catch (JMSException jmse) {
            System.out.println("Error al mandar las acciones y/o las noticias");
            return;
        }

        try {
            stockBroker.closeConnection();
            stockNews.closeConnection();
        } catch (JMSException jmse) {
            System.out.println("Error al cerrar la conexión");
        }
    }
}
