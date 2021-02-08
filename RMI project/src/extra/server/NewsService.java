package extra.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Exposed services for clients where they can get news
 *
 * @author Xiao Peng Ye
 */
public interface NewsService extends Remote {
    public static final String LOOKUP_NAME = "StockNews";
    public ArrayList<News> getNews(LocalDate date, String ticker) throws RemoteException;
}

