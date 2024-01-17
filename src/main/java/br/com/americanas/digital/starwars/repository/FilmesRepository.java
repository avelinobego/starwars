package br.com.americanas.digital.starwars.repository;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nonnull;

import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import br.com.americanas.digital.starwars.dto.FilmesDto;
import br.com.americanas.digital.starwars.dto.PlanetaDto;
import br.com.americanas.digital.starwars.entities.Filme;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class FilmesRepository {

    private final Fetch fetch;
    // private final LoadingCache<String, FilmesDto> cache;

    public FilmesRepository(final WebClient client) {
        this.fetch = new Fetch(client);

        // var loader = new CacheLoader<String, FilmesDto>() {
        //     @Override
        //     public FilmesDto load(@Nonnull String key) {
        //         return getCompletable(key);
        //     }
        // };
        // cache = CacheBuilder.newBuilder()
        //         .expireAfterAccess(5, TimeUnit.MINUTES)
        //         .build(loader);

    }

    public List<Filme> byPlaneta(PlanetaDto r) {
        return Collections.emptyList();
    }

}
