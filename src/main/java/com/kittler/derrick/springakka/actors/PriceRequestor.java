package com.kittler.derrick.springakka.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import com.kittler.derrick.springakka.service.CoinbaseService;


/**
 *
 */
public class PriceRequestor extends AbstractActor {

    private final ActorRef printerActor;
    private final CoinbaseService coinbaseService;

    /**
     * Constructor
     * @param printerActor
     * @param coinbaseService
     */
    public PriceRequestor(ActorRef printerActor, CoinbaseService coinbaseService) {
        this.printerActor = printerActor;
        this.coinbaseService = coinbaseService;
    }

    /**
     *
     * @param printerActor
     * @param coinbaseService
     * @return
     */
    public static Props props(ActorRef printerActor, CoinbaseService coinbaseService) {
        return Props.create(
                PriceRequestor.class,
                ()->new PriceRequestor(printerActor, coinbaseService));
    }

    /**
     *
     * @return
     */
    @Override
    public Receive createReceive() {
        return receiveBuilder().match(GetThisCryproPrice.class, what ->
        printerActor
                .tell(new Printer
                        .CryptoPrice(coinbaseService
                        .getCryptoPrice(what.whatPrice)), getSelf())).build();
    }

    public static class GetThisCryproPrice {

        public final String whatPrice;

        /**
         * Constructor
         * @param whatPrice
         */
        public GetThisCryproPrice(String whatPrice) {
            this.whatPrice = whatPrice;
        }
    }
}
