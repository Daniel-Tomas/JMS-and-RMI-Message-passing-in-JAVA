package extra.server;

import extra.client.ClientInfo;

import java.io.File;
import java.io.FileNotFoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Represents communication between customers and the investment bank.
 *
 * @author Xiao Peng Ye, Daniel Tomas Sanchez, Aaron Cabero Blanco, Juan Diego Valencia Marin
 */
public class StockBroker extends UnicastRemoteObject implements BrokerService {
    /**
     * ArrayList that stores all the clients who wish to receive the daily quote information
     */
    private final ArrayList<ClientInfo> clients;
    /**
     * ArrayList that stores all the shares purchased by each client
     */
    private final HashMap<ClientInfo, HashMap<String, Value>> wallets;
    /**
     * Hash table where all enterprises are registered.
     */
    private final HashMap<String, Enterprise> enterprises;
    /**
     * List where all quotations parsed from text are stored.
     */
    private final LinkedList<Quotation> quotations;
    /**
     * Formatter used to parse date string into a LocalTime object.
     */
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    /**
     * number of clients to be connected
     */
    private int numberClients;
    /**
     * Means if all clients are connected
     */
    private boolean done;


    /**
     * Init the all base date
     *
     * @param numberClients the number clients
     * @throws RemoteException       if fail the connection
     * @throws FileNotFoundException if fails storeAllQuotations().
     */
    public StockBroker(int numberClients) throws RemoteException, FileNotFoundException {
        super();
        this.numberClients = numberClients;
        this.done = false;
        this.enterprises = new HashMap<>();
        this.quotations = new LinkedList<>();
        this.enterprises.put("AAPL", new Enterprise("AAPL", "Apple Inc", "Technology"));
        this.enterprises.put("GOOGL", new Enterprise("GOOGL", "Alphabet Inc", "Technology"));
        this.enterprises.put("TEVA", new Enterprise("TEVA", "Teva Pharmaceutical Industries Ltd.", "Health"));
        this.enterprises.put("RO", new Enterprise("RO", "Roche Holding AG", "Health"));
        this.enterprises.put("REP", new Enterprise("REP", "Repsol SA", "Energy"));
        this.enterprises.put("GALP", new Enterprise("GALP", "Galp Energia SGPS S/A", "Energy"));
        this.storeAllQuotations();

        this.clients = new ArrayList<>();
        this.wallets = new HashMap<>();
    }

    /**
     * Register a Client
     *
     * @throws RemoteException if fail the connection
     * @author Xiao Peng Ye
     */
    @Override
    public void registerClient(ClientInfo client) throws RemoteException {
        this.clients.add(client);
        this.wallets.put(client, new HashMap<>());
        this.numberClients--;
        if (this.numberClients == 0) {
            this.done = true;
        }
    }

    /**
     * Is connected clients boolean.
     *
     * @return the boolean
     */
    public boolean isConnectedClients() {
        return this.done;
    }

    /**
     * Prints the client's investment
     *
     * @throws RemoteException the remote exception
     * @author Sergio Sánchez-Carvajales Francoy
     */
    public void printWallet() throws RemoteException {
        for (HashMap.Entry<ClientInfo, HashMap<String, Value>> clientWallet : wallets.entrySet()) {
            System.out.println("Cliente " + clientWallet.getKey().getType() + ":");
            System.out.format("%-20s%-20s%-20s%-20s\n", "Ticker", "#Acciones", "Coste", "Valor");
            for (HashMap.Entry<String, Value> entry : clientWallet.getValue().entrySet()) {
                System.out.format("%-20s%-20d%-20.2f%-20.2f\n", entry.getKey(), entry.getValue().getShare(), entry.getValue().getCost(), enterprises.get(entry.getKey()).getPrice() * entry.getValue().getShare());
            }
            System.out.println("-----------------------------------------------------------------------");
        }
    }

    /**
     * Makes the action of buying shares.
     *
     * @param client The client
     * @param ticker The ticker of the enterprise
     * @param share  The number of shares the client is going to buy
     * @author Aaron Cabero Blanco
     */
    private void buyShare(ClientInfo client, String ticker, int share) {
        HashMap<String, Value> wallet = wallets.get(client);
        float price = enterprises.get(ticker).getPrice();
        if (!wallet.containsKey(ticker)) {
            wallet.put(ticker, new Value(share, price));
        } else {
            Value value = wallet.get(ticker);
            value.setShare(value.getShare() + share);
            value.setCost(value.getCost() + share * price);
        }
    }


    /**
     * Gets enterprise.
     *
     * @param ticker the ticker
     * @return the enterprise
     * @author Juan Diego Valencia Marin
     */
    private Enterprise getEnterprise(String ticker) {
        return this.enterprises.get(ticker);
    }

    /**
     * Stores all quotations from stock_prices.txt in a LinkedList attribute.
     *
     * @throws FileNotFoundException if fails when the file can not be found.
     * @author Daniel Tomas Sanchez
     */
    private void storeAllQuotations() throws FileNotFoundException {
        Scanner sc = new Scanner(new File("stock_prices.txt"));

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] parts = line.split(" ");

            LocalDate date = LocalDate.parse(parts[0], StockBroker.dateFormatter);
            Enterprise enterprise = this.getEnterprise(parts[1]);
            float cost = Float.parseFloat(parts[2]);
            float exchange = Float.parseFloat(parts[3]);
            this.quotations.add(new Quotation(enterprise.getTicker(), enterprise.getSector(), date, cost, exchange));
        }
    }

    /**
     * Sends daily quotations contained in a list to registered clients if there are days remained.
     * Otherwise do nothing.
     *
     * @return returns true if it has quotations to send
     * @author Xiao Peng Ye
     */
    public boolean sendQuotation() {
        Quotation quotation = this.quotations.poll();
        if (quotation == null) return false;
        enterprises.get(quotation.getTicker()).setPrice(quotation.getCost());
        try {
            int share;
            for (ClientInfo client : this.clients) {
                share = client.info(quotation);
                if (share != 0) {
                    buyShare(client, quotation.getTicker(), share);
                }
            }
        } catch (RemoteException e) {
            System.out.println("Error al enviar una cuotización");
            e.printStackTrace();
        }
        return true;
    }


    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        try {
            StockBroker st = new StockBroker(3);
            Registry registry = LocateRegistry.createRegistry(1100);
            registry.rebind(BrokerService.LOOKUP_NAME, st);
            while (!st.isConnectedClients()) {
                Thread.sleep(100);
            }
            while (st.sendQuotation()) ;
            st.printWallet();
        } catch (RemoteException e) {
            System.out.println("Error al establecer la conexión");
        } catch (FileNotFoundException e) {
            System.out.println("Error al abrir el archivo stock_price.txt");
        } catch (InterruptedException e) {
            System.out.println("Error al esperar a los clientes");
        }
    }
}