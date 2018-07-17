package com.kittler.derrick.springakka.actors;

import akka.actor.AbstractActor;
import akka.actor.Props;
import com.kittler.derrick.springakka.model.CoinBaseResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public class Printer extends AbstractActor {


    /**
     * Constructor
     */
    public Printer() {
    }

    public static Props props(){
        return Props.create(Printer.class, ()-> new Printer());
    }

    /**
     *
     * @return
     */
    @Override
    public Receive createReceive() {
        return receiveBuilder().match(CryptoPrice.class, msg->
                msg.message.subscribe(coinBaseResponse->
                System.out.println("[ "+LocalDateTime.now()+" ] "
                        + coinBaseResponse.getData().getBase()
                        + " Buy Price:$ "+coinBaseResponse.getData().getAmount()
                        + " " + coinBaseResponse.getData().getCurrency()))).build();
    }

    /**
     *
     */
    public static class CryptoPrice {
        public final Mono<CoinBaseResponse> message;

        /**
         * Constructor
         * @param message
         */
        public CryptoPrice(Mono<CoinBaseResponse> message) {
            this.message = message;
        }
    }
}
