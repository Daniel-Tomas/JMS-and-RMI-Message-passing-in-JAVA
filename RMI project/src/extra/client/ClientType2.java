package extra.client;

import extra.server.Quotation;
import extra.server.News;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * It creates a client of type two. This kind of client is interested in Google, Roche and Repsol.
 * Every two days he buys two shares. This class extends from extra.client.Client
 *
 * @author Aarón Cabero Blanco
 */
public class ClientType2 extends Client {
    private int day;
    private LocalDate date;

    /**
     * Creates a client of type two with the same attributes as extra.client.Client
     *
     * @throws RemoteException   if there is an error while connecting
     * @throws NotBoundException if there is no bound
     */
    public ClientType2() throws RemoteException, NotBoundException {
        super();
        TYPE = 2;
        this.day = 1;
        this.date = null;
    }

    /**
     * Buys two actions each two days from enterprises as GOOGL, REP y RO if the there are two or
     * more good news of a specific ticker.
     *
     * @param quotation quotation that the client has to process and decide how many shares wants to buy.
     * @return numbers of shares the client wants to buy of a specific ticker
     * @author Aarón Cabero Blanco
     */
    @Override
    public int info(Quotation quotation) throws RemoteException {
        if (this.date != null && !this.date.equals(quotation.getDate())) {
            this.day++;
        }
        this.date = quotation.getDate();
        if (day % 2 == 1) {
            String ticker = quotation.getTicker();
            if (ticker.equals("GOOGL") || ticker.equals("RO") || ticker.equals("REP")) {
                ArrayList<News> news = servicen.getNews(this.date, ticker);
                if (news == null) {
                    return 0;
                }
                int goodNews = 0;
                for (News dailyNew : news) {
                    if (getOpinion(dailyNew.getContent()))
                        goodNews++;
                }
                if (goodNews >= 2)
                    return 2;
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        try {
            ClientType2 client2 = new ClientType2();
        } catch (RemoteException | NotBoundException e) {
            System.out.println("Error al establecer la conexión");
            e.printStackTrace();
        }
    }
}