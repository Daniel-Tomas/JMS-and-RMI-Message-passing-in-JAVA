package basica.client;

import basica.server.*;

import java.rmi.*;

/**
 * It creates a client of type one. This kind of client is interested in Technology and Health
 * companies and each day he buys one share of one of them. This class extends from basica.client.Client.
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
     * Buys shares of Technology and Health sector's company
     *
     * @param quotation quotation that the client has to process and decide how many shares wants to buy
     * @return numbers of share wants to buy
     * @throws RemoteException if there is an error while connecting
     * @author Sergio Sánchez-Carvajales Francoy
     */
    public int info(Quotation quotation) throws RemoteException {
        String sector = quotation.getSector();
        if (sector.equals("Technology") || sector.equals("Health")) {
            return 1;
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
