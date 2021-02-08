package extra.client;

import extra.server.Quotation;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Exposed service for the StockBroker server cant send information of the daily quotes to the clients
 *
 * @author Xiao Peng Ye
 */
public interface ClientInfo extends Remote {
    public int getType() throws RemoteException;
    public int info(Quotation quotation) throws RemoteException;
}
