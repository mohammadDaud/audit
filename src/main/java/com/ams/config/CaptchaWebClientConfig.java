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
public class CaptchaWebClientConfig {

	@Value("${google.recaptcha.verify.url}")
	public String recaptchaVerifyUrl;
	
	@Bean(name = "captchaWebClient")
	public WebClient webClient() {

		return WebClient.builder()
				.filter(logRequest())
				.filter(logResponse())
				.baseUrl(recaptchaVerifyUrl)
				.build();
	}

	private ExchangeFilterFunction logRequest() {

		return ExchangeFilterFunction.ofRequestProcessor(request -> {
			log.info("===========REQUEST to Google for captcha============");
			log.info("Request :{} {}", request.method(), request.url());
			log.info("===========REQUEST============");
			return Mono.just(request);
		});
	}

	private ExchangeFilterFunction logResponse() {

		return ExchangeFilterFunction.ofResponseProcessor(response -> {
			log.info("===========RESPONSE from Google for captcha============");
			log.info("Response :{}", response.rawStatusCode());
			log.info("===========RESPONSE============");
			return Mono.just(response);
		});
	}
}
