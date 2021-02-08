package basica;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import java.util.ArrayList;

/**
 * It creates a client of type three. This kind of client is interested in buying shares
 * that have lost less than 5% at the end of the day. Each day he buys one share.
 * This class extends from basica.Client
 *
 * @author Sergio Sánchez-Carvajales Francoy, Juan Diego Valencia Marin, Daniel Tomas Sanchez
 */

public class ClientType3 extends Client {

    public ClientType3() {
        super();
    }

    /**
     * Buys shares of each company that have a maximum drop on the day of less than -5%
     *
     * @param message The message that the client has receive from the basica.StockBroker
     * @author Juan Diego Valencia Marin
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
                    float exchange;
                    for (Quotation quotation : quotations) {
                        exchange = quotation.getExchange();

                        if (exchange < (-5.0)) {
                            buyShare(quotation.getEnterprise().getTicker(), 1, quotation.getCost());
                        } else
                            updatePrice(quotation.getEnterprise().getTicker(), quotation.getCost());
                    }
                } catch (Exception e) {
                    System.out.println("Error en el tratamiento de la información");
                }
            } else {
                this.finish();
            }
        }

    }

    public static void main(String[] args) {
        Client client3 = new ClientType3();

        try {
            client3.startConnection();
        } catch (JMSException jmse) {
            System.out.println("Error al establecer la conexión");
        }

        while (!client3.isFinished()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println("Error al esperar la conexión");
            }
        }

        try {
            client3.closeConnection();
        } catch (JMSException jmse) {
            System.out.println("Error al cerrar la conexión");
        }

        System.out.println("Cliente 3:");
        client3.printInvestment();

    }

}
