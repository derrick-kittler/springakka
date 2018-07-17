package com.kittler.derrick.springakka.actors;

import akka.actor.AbstractActorWithTimers;
import akka.actor.ActorRef;
import akka.actor.Props;

import java.time.Duration;

/**
 * Poller Actor to poll the coinbase API
 */
public class Poller extends AbstractActorWithTimers {

    private static Object TICK_KEY = "TickKey";
    private final ActorRef requestorActor;
    private final String cryptoName;

    /**
     * Default constructor
     *
     * @param requestorActor
     * @param cryptoName
     */
    public Poller(ActorRef requestorActor, String cryptoName) {
        this.requestorActor = requestorActor;
        this.cryptoName = cryptoName;
        getTimers().startSingleTimer(TICK_KEY, new FirstTick(), Duration.ofMillis(3000));
    }

    /**
     * @param cryptoName
     * @param requestActor
     * @return
     */
    public static Props props(String cryptoName, ActorRef requestActor) {
        return Props.create(Poller.class, () -> new Poller(requestActor, cryptoName));
    }

    /**
     * Return the crypto price on a 3 second interval.
     * @return
     */
    @Override
    public Receive createReceive() {
        return receiveBuilder().match(
                FirstTick.class, message -> {
                    getTimers().startPeriodicTimer(TICK_KEY, new Tick(), Duration.ofSeconds(3));
                }).match(
                Tick.class, message ->
                        requestorActor.tell(new PriceRequestor
                                        .GetThisCryproPrice(cryptoName), getSelf())
        ).build();
    }

    private static final class FirstTick {
    }

    private static final class Tick {
    }
}


