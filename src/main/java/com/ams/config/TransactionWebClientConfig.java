package com.ams.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@Configuration
@Log4j2
public class TransactionWebClientConfig {

	@Value("${transaction.base.url}")
	public String transactionBaseUrl;
	

	@Value("${transaction.username}")
	public String username;
	

	@Value("${transaction.password}")
	public String password;
	
	@Bean(name = "transactionWebClient")
	public WebClient webClientTransaction() {

		return WebClient.builder()
				.filter(logRequest())
				.filter(logResponse())
				.baseUrl(transactionBaseUrl)
			    .defaultHeaders(header -> header.setBasicAuth(username, password))
				.build();
	}

	private ExchangeFilterFunction logRequest() {

		return ExchangeFilterFunction.ofRequestProcessor(request -> {
			
			log.info("===========REQUEST to Transaction processing system=====");
			log.info("Request :{} {}", request.method(), request.url());
			request.headers().forEach((name, values) -> values.forEach(value -> log.info("{}={}", name, value)));
			log.info("===========REQUEST============");
			return Mono.just(request);
		});
	}

	private ExchangeFilterFunction logResponse() {

		return ExchangeFilterFunction.ofResponseProcessor(response -> {
			log.info("===========RESPONSE from Transaction processing=========");
	        log.info("Response: {}", response.headers().asHttpHeaders().get("property-header"));

			log.info("Response :{}", response.rawStatusCode());
			log.info("===========RESPONSE============");
			return Mono.just(response);
		});
	}
}
