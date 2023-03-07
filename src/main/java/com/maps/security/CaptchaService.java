//package com.maps.security;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.reactive.function.BodyInserters;
//import org.springframework.web.reactive.function.client.WebClient;
//import com.maps.api.admin.model.RecaptchaResponse;
//import lombok.RequiredArgsConstructor;
//
//@Service
//@RequiredArgsConstructor
//public class CaptchaService {
//
//	private final WebClient captchaWebClient;
//
//	@Value("${google.recaptcha.secret.key}")
//	public String recaptchaSecret;
//
//	public boolean verify(String response) {
//		MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
//		param.add("secret", recaptchaSecret);
//		param.add("response", response);
//
//		return captchaWebClient.post().body(BodyInserters.fromObject(param)).exchange().flatMap(clientResponse -> {
//			return clientResponse.bodyToMono(RecaptchaResponse.class);
//		}).block().isSuccess();
//	}
//}