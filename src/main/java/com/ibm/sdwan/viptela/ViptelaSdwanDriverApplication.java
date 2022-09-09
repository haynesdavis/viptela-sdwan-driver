package com.ibm.sdwan.viptela;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import  com.ibm.sdwan.viptela.config.SDWDriverProperties;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
@SpringBootApplication
@Slf4j
public class ViptelaSdwanDriverApplication {

	public static void main(String[] args) {
		SpringApplication.run(ViptelaSdwanDriverApplication.class, args);
	}
    
	@Autowired
	SDWDriverProperties sDWDriverProperties;
	@Bean
	public RestTemplate getRestTemplate(RestTemplateBuilder restTemplateBuilder) {
		return restTemplateBuilder
				.requestFactory(this::disableSSL)
				.setConnectTimeout(sDWDriverProperties.getRestConnectTimeout())
				.setReadTimeout(sDWDriverProperties.getRestReadTimeout())
				.build();
	}

	private HttpComponentsClientHttpRequestFactory disableSSL(){
		TrustStrategy acceptedTrustStrategy = (x509Certificates, s) -> true;
		SSLContext sslContext = null;

		try {
			sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptedTrustStrategy).build();
		} catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
			e.printStackTrace();
		}
		SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
		CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		requestFactory.setHttpClient(httpClient);
		return requestFactory;
	}

}
