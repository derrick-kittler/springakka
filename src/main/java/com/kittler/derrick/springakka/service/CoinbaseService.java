package com.kittler.derrick.springakka.service;


import com.kittler.derrick.springakka.model.CoinBaseResponse;
import reactor.core.publisher.Mono;

public interface CoinbaseService {

  Mono<CoinBaseResponse> getCryptoPrice(String priceName);
}
