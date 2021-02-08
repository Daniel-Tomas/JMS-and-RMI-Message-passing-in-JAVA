package extra.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


/**
 * Server that provides news to clients
 *
 * @author Xiao Peng Ye, Daniel Tomas Sanchez
 */
public class StockNews extends UnicastRemoteObject implements NewsService {
    /**
     * Database of the news organised by date
     */
    private final HashMap<LocalDate, HashMap<String, ArrayList<News>>> allNews;
    /**
     * Formatter used to parse date string into a LocalTime object.
     */
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");


    /**
     * Init the all base date
     *
     * @throws RemoteException       if fail the connection
     * @throws FileNotFoundException if fails storeAllNews().
     */
    protected StockNews() throws RemoteException, FileNotFoundException {
        super();
        this.allNews = new HashMap<>();
        this.storeAllNews();
    }

    /**
     * Get a ArrayList of news indicating the date and company
     *
     * @param date of the news is required
     * @param ticker of the news is required
     * @return news from the database with requirements, null otherwise
     * @throws RemoteException if fail the connection
     * @author Xiao Peng Ye, Daniel Tomas Sanchez
     */
    @Override
    public ArrayList<News> getNews(LocalDate date, String ticker) throws RemoteException {
        return this.allNews.get(date).get(ticker);
    }

    /**
     * Stores all news from stock_news.txt in allNews attribute.
     *
     * @throws FileNotFoundException when the file doesn't exist
     * @author Daniel Tomas Sanchez
     */
    private void storeAllNews() throws FileNotFoundException {
        Scanner source = new Scanner(new File("stock_news.txt"));

        LocalDate prevDate = null;
        HashMap<String, ArrayList<News>> dailyNews = null;
        ArrayList<News> tickerNews;
        while (source.hasNextLine()) {

            String line = source.nextLine();
            String[] parts = line.split("-");
            LocalDate date = LocalDate.parse(parts[0].trim(), StockNews.dateFormatter);
            String ticker = parts[1].trim();
            String[] contents = parts[2].split(",");

            if (!date.equals(prevDate)) {
                if (prevDate != null)
                    this.allNews.put(prevDate, dailyNews);

                dailyNews = new HashMap<>();
                prevDate = date;
            }
            tickerNews = dailyNews.get(ticker);
            if (tickerNews == null)
                tickerNews = new ArrayList<>();

            tickerNews.add(new News(ticker, date, contents[1].trim()));
            dailyNews.put(ticker, tickerNews);
        }
        this.allNews.put(prevDate, dailyNews);
    }

    public static void main(String[] args) {
        try {
            StockNews sn = new StockNews();
            Registry registry = LocateRegistry.createRegistry(1101);
            registry.rebind(NewsService.LOOKUP_NAME, sn);
        } catch (RemoteException e) {
            System.out.println("Error al establecer la conexión");
        } catch (FileNotFoundException e) {
            System.out.println("Error al abrir el archivo stock_news.txt");
        }
    }

}

