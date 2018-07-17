package com.kittler.derrick.springakka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication

/**
 * A simple application that uses both Spring and Akka to poll the coinbase API
 * and return current buy prices for both BTC an ETH coins. This was build with
 * guidance from @Chris Anatalio from Lynda.
 */
public class SpringakkaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringakkaApplication.class, args);
	}
}
