package basica;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import java.util.ArrayList;

/**
 * It creates a client of type two. This kind of client is interested in Google, Roche and Repsol.
 * Every two days he buys two shares. This class extends from basica.Client
 *
 * @author Sergio Sánchez-Carvajales Francoy, Aarón Cabero Blanco, Juan Diego Valencia Marin, Daniel Tomas Sanchez
 */

public class ClientType2 extends Client {
    private int dia;

    /**
     * Creates a client of type two with the same attributes as basica.Client
     */
    public ClientType2() {
        super();
        dia = 1;
    }

    /**
     * Buys two actions each two days from enterprises as GOOGL, REP y RO
     *
     * @param message The message that the client has receive from the basica.StockBroker.
     * @author Aarón Cabero Blanco
     */
    public void onMessage(Message message) {

        if (message instanceof ObjectMessage) {
            ObjectMessage objMessage = (ObjectMessage) message;
            Object obj = null;
            try {
                obj = objMessage.getObject();
            } catch (JMSException e) {
                System.out.println("Información inválida");
            }
            if (obj != null) {

                try {
                    @SuppressWarnings("unchecked")
                    ArrayList<Quotation> quotations = (ArrayList<Quotation>) obj;
                    Enterprise enterprise;
                    for (Quotation quotation : quotations) {
                        enterprise = quotation.getEnterprise();
                        String ticker = enterprise.getTicker();
                        if (dia % 2 == 0)
                            updatePrice(enterprise.getTicker(), quotation.getCost());
                        else if (ticker.equals("GOOGL") || ticker.equals("RO") || ticker.equals("REP")) {
                            buyShare(ticker, 2, quotation.getCost());
                        }
                    }

                    dia++;
                } catch (Exception e) {
                    System.out.println("Error en el tratamiento de la información");
                }
            } else {
                this.finish();
            }
        }
    }

    public static void main(String[] args) {
        Client client2 = new ClientType2();

        try {
            client2.startConnection();
        } catch (JMSException jmse) {
            System.out.println("Error al establecer la conexión" );
        }

        while (!client2.isFinished()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println("Error al esperar la conexión");
            }
        }

        try {
            client2.closeConnection();
        } catch (JMSException jmse) {
            System.out.println("Error al cerrar la conexión");
        }

        System.out.println("Cliente 2:");
        client2.printInvestment();

    }

}
