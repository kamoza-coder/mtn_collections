package com.mtn.services;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class WebClientService {
    private final int timeout = 15000;
    private HttpClient httpClient;

    @PostConstruct
    public void setUpTimeout() {
        httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, timeout)
                .responseTimeout(Duration.ofMillis(timeout))
                .doOnConnected(conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(timeout, TimeUnit.MILLISECONDS))
                                .addHandlerLast(new WriteTimeoutHandler(timeout, TimeUnit.MILLISECONDS)));
    }


    public WebClient requestWithEndpoint(String endpoint) {
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .baseUrl(endpoint)
                .build();
    }

}
