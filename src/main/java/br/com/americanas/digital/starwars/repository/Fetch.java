package br.com.americanas.digital.starwars.repository;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import io.micrometer.common.util.StringUtils;
import reactor.core.publisher.Mono;

public final class Fetch {

    private final WebClient webClient;

    public Fetch(WebClient webClient) {
        this.webClient = webClient;
    }

    public <T> Mono<T> exec(String url, Class<T> type) {
        if (StringUtils.isBlank(url)) {
            return Mono.empty();
        }
        return webClient
                .get()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(type);
    }
}
