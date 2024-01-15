package br.com.americanas.digital.starwars.repository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.apache.commons.collections4.ListUtils;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

import com.google.common.cache.Cache;

import br.com.americanas.digital.starwars.dto.FilmesDto;
import br.com.americanas.digital.starwars.dto.PlanetaDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class FilmesRepository {

    private final Fetch fetch;
    private final Cache<String, Object> cache;

    public FilmesRepository(final WebClient client, final Cache<String, Object> cache) {
        this.fetch = new Fetch(client);
        this.cache = cache;
    }

    public List<FilmesDto> byPlaneta(PlanetaDto r) {

        var lista = ListUtils.emptyIfNull(r.filmes()).stream()
                .map(str -> {
                    if (str == null) {
                        return null;
                    }

                    if (cache.getIfPresent(str) == null) {
                        var completable = CompletableFuture
                                .supplyAsync(() -> fetch.exec(str, FilmesDto.class).block());
                        try {
                            cache.put(str, completable.get());
                        } catch (InterruptedException | ExecutionException e) {
                            log.error("Erro ao obter filmes", e);
                        }

                    }
                    return (FilmesDto) cache.getIfPresent(str);
                })
                .filter(e -> !Objects.isNull(e))
                .toList();

        return lista;
    }

}
