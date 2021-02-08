package basica.server;

import basica.client.ClientInfo;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Exposed services for clients where they can register
 *
 * @author Xiao Peng Ye
 */
public interface BrokerService extends Remote {
    public static final String LOOKUP_NAME = "StockBroker";
    public void registerClient(ClientInfo client) throws RemoteException;
}
