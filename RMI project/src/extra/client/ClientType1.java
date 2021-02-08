package extra.client;

import extra.server.Quotation;
import extra.server.News;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * It creates a client of type one. This kind of client is interested in Technology and Health
 * companies and each day he buys one share of one of them. This class extends from extra.client.Client.
 *
 * @author Sergio Sánchez-Carvajales Francoy
 */
public class ClientType1 extends Client {

    /**
     * Creates a client of type one with the same attributes as basica.client.Client
     *
     * @throws RemoteException   if there is an error while connecting
     * @throws NotBoundException if there is no bound
     * @author Sergio Sánchez-Carvajales Francoy
     */

    public ClientType1() throws RemoteException, NotBoundException {
        super();
        TYPE = 1;
    }

    /**
     * Buys one share for each good new of Technology and Health sector's company.
     *
     * @param quotation quotation that the client has to process and decide how many shares wants to buy
     * @return numbers of share wants to buy
     * @author Sergio Sánchez-Carvajales Francoy
     */
    @Override
    public int info(Quotation quotation) throws RemoteException {

        String sector = quotation.getSector();
        if (sector.equals("Technology") || sector.equals("Health")) {
            ArrayList<News> news = servicen.getNews(quotation.getDate(), quotation.getTicker());
            if (news == null) {
                return 0;
            }
            int goodNews = 0;
            for (News dailyNew : news) {
                if (getOpinion(dailyNew.getContent()))
                    goodNews++;
            }
            return goodNews;
        }

        return 0;
    }

    public static void main(String[] args) {
        try {
            ClientType1 client1 = new ClientType1();
        } catch (RemoteException | NotBoundException e) {
            System.out.println("Error al establecer la conexión");
            e.printStackTrace();
        }
    }
}
