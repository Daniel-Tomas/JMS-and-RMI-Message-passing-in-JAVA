package basica;

import java.util.*;
import javax.jms.*;

/**
 * It creates a client of type one. This kind of client is interested in Technology and Health
 * companies and each day he buys one share of one of them. This class extends from basica.Client.
 *
 * @author Sergio Sánchez-Carvajales Francoy, Juan Diego Valencia Marin, Daniel Tomas Sanchez
 */

public class ClientType1 extends Client {

    public ClientType1() {
        super();
    }


    /**
     * It creates a client of type one. This kind of client is interested in Technology and Health
     * companies and each day he buys one share of one of them. This class extends from basica.Client.
     *
     * @param message The message that the client has receive from the basica.StockBroker.
     *
     * @author Sergio Sánchez-Carvajales Francoy, Juan Diego Valencia Marin, Daniel Tomas Sanchez
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
                        String sector = enterprise.getSector();

                        if (sector.equals("Technology") || sector.equals("Health")) {
                            buyShare(enterprise.getTicker(), 1, quotation.getCost());
                        } else {
                            updatePrice(enterprise.getTicker(), quotation.getCost());
                        }
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
        Client client1 = new ClientType1();

        try {
            client1.startConnection();
        } catch (JMSException jmse) {
            System.out.println("Error al establecer la conexión");
        }

        while (!client1.isFinished()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println("Error al esperar la información");
            }
        }

        try {
            client1.closeConnection();
        } catch (JMSException jmse) {
            System.out.println("Error al cerrar la conexión");
        }

        System.out.println("Cliente 1:");
        client1.printInvestment();

    }

}
