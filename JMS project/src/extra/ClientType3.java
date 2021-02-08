package extra;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * It creates a client of type three. This kind of client is interested in buying shares
 * that have lost less than 5% at the end of the day. Each day he buys one share.
 * This class extends from extra.Client
 *
 * @author Juan Diego Valencia Marin
 */

public class ClientType3 extends Client {

    public ClientType3() {
        super();
    }

    /**
     * Buys shares of each company that have a maximum drop in the day of less than -5%
     * and receives one or more of a negative news from said company
     *
     * @author Juan Diego Valencia Marin
     */
    protected void makePurchase() {
        float exchange;
        float cost;
        String ticker;
        int badNews = 0;
        ArrayList<News> tickerNews;

        for (Quotation quotation : quotations) {
            exchange = quotation.getExchange();
            ticker = quotation.getEnterprise().getTicker();
            cost = quotation.getCost();
            updatePrice(ticker, cost);

            if (exchange < (-5.0)) {
                tickerNews = news.get(ticker);
                if (tickerNews == null) continue;
                for (News dailyNew : tickerNews) {
                    if (!getOpinion(dailyNew.getContent())) {
                        badNews++;
                    }
                }
                if (badNews >= 1) {
                    buyShare(ticker, 1, cost);
                }
            }
        }
    }

    /**
     * Compare the news and the movement of shares, and buy shares
     *
     * @param message The message that the client has receive from the extra.StockBroker
     * @author Juan Diego Valencia Marin
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
                    if (obj instanceof ArrayList<?>) {
                        this.quotations = (ArrayList<Quotation>) obj;
                    } else if (obj instanceof HashMap<?, ?>) {
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
        Client client3 = new ClientType3();

        try {
            client3.startConnection();
        } catch (JMSException jmse) {
            System.out.println("Error la establecer la conexión");
        }

        while (!client3.isFinished()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println("Error al esperar la información");
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
