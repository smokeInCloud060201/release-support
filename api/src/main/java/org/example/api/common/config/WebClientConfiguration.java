package org.example.api.common.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.api.common.config.properties.JiraProperty;
import org.example.api.common.config.properties.KeyStoreProperty;
import org.example.api.common.dto.request.QueryParam;
import org.example.api.common.dto.request.URLComponent;
import org.example.api.common.enums.WebClientType;
import org.example.api.common.util.EncryptUtil;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.time.Duration;
import java.util.Base64;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebClientConfiguration {

    private final JiraProperty jiraProperty;
    private final KeyStoreProperty keyStoreProperty;
    private final EncryptUtil encryptUtil;

    private static final int MAX_IN_MEMORY_SIZE = 1024 * 1024 * 20;
    private static final int TIMEOUT = 500000;

    HttpClient httpClient = HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, TIMEOUT)
            .responseTimeout(Duration.ofMillis(TIMEOUT))
            .doOnConnected(conn ->
                    conn.addHandlerLast(new ReadTimeoutHandler(TIMEOUT, TimeUnit.MILLISECONDS))
                            .addHandlerLast(new WriteTimeoutHandler(TIMEOUT, TimeUnit.MILLISECONDS)));
    private final WebClient client = WebClient.builder()
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .exchangeStrategies(ExchangeStrategies.builder()
                    .codecs(codec -> codec.defaultCodecs().maxInMemorySize(MAX_IN_MEMORY_SIZE))
                    .build())
            .build();

    public String getAuthenticationHeader(WebClientType type) {
        try {
            switch (type) {
                case JIRA -> {
                    String tokenHashing = jiraProperty.getUser().getToken();
                    String emailHashing = jiraProperty.getUser().getEmail();
                    PrivateKey privateKey = encryptUtil.loadPrivateKey(keyStoreProperty.getPvk());
                    return Base64.getEncoder().encodeToString((encryptUtil.decrypt(emailHashing, privateKey) + ":" + encryptUtil.decrypt(tokenHashing, privateKey)).getBytes(StandardCharsets.UTF_8));
                }
                case GITHUB -> {
                    return "";
                }

                default -> {
                    log.info("Unknown web client type");
                    return "Unknown";
                }
            }
        } catch (Exception e) {
            log.error("Error occurred while generating authentication header", e);
            return "Unknown";
        }

    }

    public <T, R> CompletableFuture<R> postRequest(String url, T input, ParameterizedTypeReference<R> responseType, WebClientType webClientType) {
        return client.post()
                .uri(URI.create(url))
                .body(BodyInserters.fromPublisher(Mono.just(input), new ParameterizedTypeReference<>() {
                }))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "Basic " + getAuthenticationHeader(webClientType))
                .exchangeToMono(response -> {
                    if (response.statusCode().is2xxSuccessful()) {
                        return response.bodyToMono(responseType);
                    } else {
                        return response.bodyToMono(String.class).flatMap(error -> {
                            log.error("Post request ERROR {}", error);
                            return Mono.error(new RuntimeException(error));
                        });
                    }
                })
                .toFuture();
    }

    public <R> CompletableFuture<R> getRequest(URLComponent urlComponent, ParameterizedTypeReference<R> responseType, WebClientType webClientType) {
        return client.get()
                .uri(uriBuilder -> {
                    UriBuilder builder = uriBuilder
                            .scheme(urlComponent.getScheme())
                            .host(urlComponent.getHost())
                            .path(urlComponent.getPath());
                    for (QueryParam queryParam : urlComponent.getQueryParams()) {
                        builder = builder.queryParam(queryParam.getKey(), queryParam.getValue());
                    }
                    return builder.build();
                })
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "Basic " + getAuthenticationHeader(webClientType))
                .exchangeToMono(response -> {
                    if (response.statusCode().is2xxSuccessful()) {
                        return response.bodyToMono(responseType);
                    } else {
                        return response.bodyToMono(String.class).flatMap(error -> {
                            log.error("Get request ERROR {}", error);
                            return Mono.error(new RuntimeException(error));
                        });
                    }
                })
                .toFuture();
    }

}
