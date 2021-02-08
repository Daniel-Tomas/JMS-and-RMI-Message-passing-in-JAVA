package extra.client;

import java.util.Hashtable;
import java.util.Map;

import extra.server.BrokerService;
import extra.server.NewsService;
import extra.server.Quotation;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * A class which represents a client. It's an abstract class because there will be some different clients
 * who will have different interest in buying actions.
 *
 * @author Aaron Cabero Blanco, Gonzalo Peña Rupérez, Daniel Tomas Sanchez
 */
public abstract class Client extends UnicastRemoteObject implements ClientInfo {
    protected BrokerService serviceb;
    protected NewsService servicen;
    protected static int TYPE;
    /**
     * Links news content with the opinions about them
     */
    private final Map<String, Boolean> newsOpinion;

    /**
     * To know what type of client.
     *
     * @return the type of the client between 1,2 and 3.
     * @author Aarón Cabero Blanco
     */
    public int getType() {
        return TYPE;
    }

    public Client() throws RemoteException, NotBoundException {
        super();
        Registry registry = LocateRegistry.getRegistry(1100);
        this.serviceb = (BrokerService) registry.lookup(BrokerService.LOOKUP_NAME);
        serviceb.registerClient(this);
        registry = LocateRegistry.getRegistry(1101);
        this.servicen = (NewsService) registry.lookup(NewsService.LOOKUP_NAME);
        this.newsOpinion = new Hashtable<>();

        this.newsOpinion.put("ha experimentado un aumento en las ventas", true);
        this.newsOpinion.put("ha reportado un aumento en los beneficios de la compañía", true);
        this.newsOpinion.put("ha abierto una nueva fábrica para aumentar la producción de la compañía", true);
        this.newsOpinion.put("ha reducido sensiblemente la deuda de la compañía", true);

        this.newsOpinion.put("ha sufrido un decremento en las ventas", false);
        this.newsOpinion.put("ha reportado una disminución en los beneficios de la compañía", false);
        this.newsOpinion.put("ha recibido una fuerte sanción económica del organismo regulador", false);
        this.newsOpinion.put("está alcanzando niveles preocupantes de deuda", false);
    }

    /**
     * To know how many shares the client wants to buy
     *
     * @param quotation quotation that the client has to process and decide how many shares wants to buy.
     * @return numbers of shares the client wants to buy of a specific ticker
     * @author Aarón Cabero Blanco
     */
    public abstract int info(Quotation quotation) throws RemoteException;

    /**
     * Difference if a news is good or bad.
     *
     * @param newsContent the news content.
     * @return true if the opinion is positive, false otherwise.
     * @author Daniel Tomas Sanchez
     */

    public boolean getOpinion(String newsContent) {
        return this.newsOpinion.get(newsContent);
    }
}