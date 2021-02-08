package basica.client;

import basica.server.*;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.time.LocalDate;

/**
 * It creates a client of type two. This kind of client is interested in Google, Roche and Repsol.
 * Every two days he buys two shares. This class extends from basica.client.Client
 *
 * @author Aarón Cabero Blanco
 */
public class ClientType2 extends Client {
    private int day;
    private LocalDate date;

    /**
     * Creates a client of type two with the same attributes as basica.client.Client
     *
     * @throws RemoteException   if there is an error while connecting
     * @throws NotBoundException if there is no bound
     * @author Aaron Cabero Blanco
     */
    public ClientType2() throws RemoteException, NotBoundException {
        super();
        TYPE = 2;
        this.day = 1;
        this.date = null;
    }

    /**
     * Buys two actions each two days from enterprises as GOOGL, REP y RO
     *
     * @param quotation quotation that the client has to process and decide how many shares wants to buy
     * @return numbers of share wants to buy
     * @throws RemoteException if there is an error while connecting
     * @author Aarón Cabero Blanco
     */
    public int info(Quotation quotation) throws RemoteException {
        if (this.date != null && !this.date.isEqual(quotation.getDate())) {
            this.day++;
        }
        this.date = quotation.getDate();
        if (day % 2 == 1) {
            String ticker = quotation.getTicker();
            if (ticker.equals("GOOGL") || ticker.equals("RO") || ticker.equals("REP")) {
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