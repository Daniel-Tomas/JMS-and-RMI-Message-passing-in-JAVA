package extra.client;

import extra.server.Quotation;
import extra.server.News;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * It creates a client of type three. This kind of client is interested in buying shares that have lost at most 5%
 * at the end of the day and at least 1  bad new.
 * The client buys one share for each company which meet all the requirements.
 * This class extends from extra.client.Client
 *
 * @author Juan Diego Valencia Marin
 */
public class ClientType3 extends Client {

    /**
     * Creates a client of type three with the same attributes as extra.client.Client
     *
     * @throws RemoteException   if there is an error while connecting
     * @throws NotBoundException if there is no bound
     */
    public ClientType3() throws RemoteException, NotBoundException {
        super();
        TYPE = 3;
    }

    /**
     * Buys shares of each company that have a maximum daily drop of less than -5% and at least 1 bad new
     *
     * @param quotation quotation that the client has to process and decide how many shares wants to buy
     * @return numbers of share wants to buy
     * @author Juan Diego Valencia Marin
     */
    @Override
    public int info(Quotation quotation) throws RemoteException {
        float exchange;
        exchange = quotation.getExchange();
        if (exchange < (-5.0)) {
            ArrayList<News> news = servicen.getNews(quotation.getDate(), quotation.getTicker());
            if (news == null) {
                return 0;
            }
            int badNews = 0;
            for (News dailyNew : news) {
                if (!getOpinion(dailyNew.getContent()))
                    badNews++;
            }
            if (badNews >= 1) {
                return 1;
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        try {
            ClientType3 client3 = new ClientType3();
        } catch (RemoteException | NotBoundException e) {
            System.out.println("Error al establecer la conexión");
            e.printStackTrace();
        }
    }
}
