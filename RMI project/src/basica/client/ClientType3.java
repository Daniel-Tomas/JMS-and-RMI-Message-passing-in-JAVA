package basica.client;

import basica.server.*;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * It creates a client of type three. This kind of client is interested in buying shares
 * that have lost at most  5% at the end of the day.
 * The client buys one share for each company which meet all the requirements.
 * This class extends from basica.client.Client
 *
 * @author Juan Diego Valencia Marin
 */
public class ClientType3 extends Client {

    /**
     * Creates a client of type three with the same attributes as basica.client.Client
     *
     * @throws RemoteException   if there is an error while connecting
     * @throws NotBoundException if there is no bound
     * @author Sergio Sánchez-Carvajales Francoy
     */
    public ClientType3() throws RemoteException, NotBoundException {
        super();
        TYPE = 3;
    }

    /**
     * Buys shares of each company that have a maximum daily drop of less than -5%
     *
     * @param quotation quotation that the client has to process and decide how many shares wants to buy
     * @return numbers of share wants to buy
     * @throws RemoteException if there is an error while connecting
     * @author Juan Diego Valencia Marin
     */
    public int info(Quotation quotation) throws RemoteException {
        float exchange;
        exchange = quotation.getExchange();
        if (exchange < (-5.0)) {
            return 1;
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
