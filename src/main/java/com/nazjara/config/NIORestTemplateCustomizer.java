package com.nazjara.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.client.reactive.ReactorResourceFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.util.function.Function;

@Component
public class NIORestTemplateCustomizer implements WebClientCustomizer {

    private ReactorResourceFactory resourceFactory() {
        ReactorResourceFactory factory = new ReactorResourceFactory();
        factory.setUseGlobalResources(false);
        return factory;
    }

    private ClientHttpConnector clientHttpConnector() {
        Function<HttpClient, HttpClient> mapper = client -> HttpClient.create()
                .tcpConfiguration(httpClient -> httpClient
                        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
                        .doOnConnected(conn -> conn
                                .addHandlerLast(new ReadTimeoutHandler(10))
                                .addHandlerLast(new WriteTimeoutHandler(10))));

        return new ReactorClientHttpConnector(resourceFactory(), mapper);
    }

    @Override
    public void customize(WebClient.Builder webClientBuilder) {
        webClientBuilder.clientConnector(clientHttpConnector());
    }
}