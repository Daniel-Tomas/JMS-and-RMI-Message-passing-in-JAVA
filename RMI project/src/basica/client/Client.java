package basica.client;

import basica.server.Quotation;
import basica.server.BrokerService;

import java.rmi.registry.Registry;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

/**
 * A class which represents a client. It's an abstract class because there will be some different clients
 * who will have different interest in buying actions.
 *
 * @author Aaron Cabero Blanco
 */
public abstract class Client extends UnicastRemoteObject implements ClientInfo {
    protected BrokerService service;
    protected static int TYPE;

    /**
     * To know what the type of a client
     *
     * @return the type of the client
     * @throws RemoteException if there is an error while connecting
     * @author Sergio Sánchez-Carvajales Francoy
     */
    public int getType() throws RemoteException{
        return TYPE;
    }

    public Client() throws RemoteException, NotBoundException {
        super();
        Registry registry = LocateRegistry.getRegistry();
        this.service = (BrokerService) registry.lookup(BrokerService.LOOKUP_NAME);
        service.registerClient(this);
    }
    /**
     * To know how many shares the client wants to buy
     *
     * @param quotation quotation that the client has to process and decide how many shares wants to buy.
     * @return numbers of shares the client wants to buy of a specific ticker
     * @author Aarón Cabero Blanco
     */
    public abstract int info(Quotation quotation) throws RemoteException;
}