package extra;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * It creates a client of type one. This kind of client is interested in Technology and Health
 * companies and if there is a good news he buys one share of one of them. This class extends from extra.Client.
 *
 * @author Sergio Sánchez-Carvajales Francoy
 */

public class ClientType1 extends Client {

    public ClientType1() {
        super();
    }

    /**
     * Buys shares from Health and Technology companies only if it recieves good news. He tries to buy everyday.
     *
     * @author Sergio Sánchez-Carvajales Francoy
     */
    protected void makePurchase() {
        ArrayList<News> tickerNews;
        String ticker;
        Enterprise enterprise;
        String sector;
        float cost;

        for (Quotation quotation : quotations) {
            enterprise = quotation.getEnterprise();
            ticker = enterprise.getTicker();
            sector = enterprise.getSector();
            cost = quotation.getCost();
            updatePrice(enterprise.getTicker(), cost);
            if (sector.equals("Technology") || sector.equals("Health")) {
                tickerNews = news.get(ticker);
                if (tickerNews == null) continue;
                int goodNews = 0;
                for (News dailyNew : tickerNews) {
                    if (getOpinion(dailyNew.getContent())) {
                        goodNews++;
                    }
                }
                if (goodNews != 0) {
                    buyShare(ticker, goodNews, cost);
                }
            }
        }
    }

    /**
     * Compare the news and the movement of shares, and buy shares
     *
     * @param message The message that the client has receive from the extra.StockBroker
     *
     * @author Sergio Sánchez-Carvajales Francoy
     */
    @SuppressWarnings("unchecked")
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
                    if (obj instanceof ArrayList) {
                        this.quotations = (ArrayList<Quotation>) obj;
                    } else if (obj instanceof HashMap) {
                        this.news = (HashMap<String, ArrayList<News>>) obj;
                    }

                    if (this.quotations != null && this.news != null) {
                        makePurchase();
                        this.quotations = null;
                        this.news = null;
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
