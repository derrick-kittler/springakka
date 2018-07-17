package com.kittler.derrick.springakka.cmd;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;


import com.kittler.derrick.springakka.actors.Poller;
import com.kittler.derrick.springakka.actors.PriceRequestor;
import com.kittler.derrick.springakka.actors.Printer;
import com.kittler.derrick.springakka.service.CoinbaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CmdLineUI implements CommandLineRunner {

    @Autowired
    CoinbaseService coinbaseService;

    @Override
    public void run(String... args) throws Exception {

        final ActorSystem system = ActorSystem.create("helloakka");

        System.out.println(
                "\n========================================================="
                        + "\n                                                         "
                        + "\n          Coinbase Price Service                         "
                        + "\n                                       "
                        + "\n                                                        "
                        + "\n                             "
                        + "\n=========================================================");
        System.out.println();

        /* Define all actors used */

        // printerActor
        final ActorRef printer = system
                .actorOf(Printer.props(), "printerActor");

        // requestor
        final ActorRef priceRequestor = system.actorOf(PriceRequestor
                .props(printer, coinbaseService), "requestor");

        // BTC poller
        final ActorRef poller = system
                .actorOf(Poller
                .props("BTC-USD", priceRequestor), "poller");

        // ETH poller
        final ActorRef ethPoller = system
                .actorOf(Poller
                        .props("ETH-USD", priceRequestor), "ethPoller");

    }

}