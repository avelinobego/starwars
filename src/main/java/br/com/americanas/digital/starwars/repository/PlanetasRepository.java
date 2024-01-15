package br.com.americanas.digital.starwars.repository;

import org.apache.commons.collections4.ListUtils;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

import com.google.common.cache.Cache;

import br.com.americanas.digital.starwars.entities.Filme;
import br.com.americanas.digital.starwars.entities.Planeta;
import br.com.americanas.digital.starwars.dto.PlanetaDto;
import br.com.americanas.digital.starwars.dto.RespostaDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class PlanetasRepository {

    private final Fetch fetch;
    private final FilmesRepository filmesRepository;
    private final Cache<String, Object> cache;

    public PlanetasRepository(Cache<String, Object> cache, WebClient webClient, FilmesRepository filmesRepository) {
        this.fetch = new Fetch(webClient);
        this.filmesRepository = filmesRepository;
        this.cache = cache;
    }

    public Flux<Planeta> all() {

        var fluxPlanetaDto = fetch.exec("https://swapi.dev/api/planets", RespostaDto.class)
                .expand(expander -> fetch.exec(expander.next(), RespostaDto.class))
                .flatMapIterable(r -> {
                    return r.planetas();
                });

        var fluxPlanetas = fluxPlanetaDto
                .map(r -> {
                    var lista = filmesRepository.byPlaneta(r);
                    return new Planeta(r.id(), r.nome(), r.clima(), r.terreno(), lista.stream()
                            .map(d -> {
                                if (cache.getIfPresent(d.url()) == null) {
                                    cache.put(
                                            d.url(),
                                            new Filme(
                                                    d.titulo(),
                                                    d.episodio(),
                                                    d.abertura(),
                                                    d.estreia()));
                                }
                                return (Filme) cache.getIfPresent(d.url());
                            })
                            .toList());
                });

        return fluxPlanetas;

    }

    public Mono<Planeta> byId(Long id) {
        return fetch.exec(String.format("https://swapi.dev/api/planets/%d/", id), PlanetaDto.class)
                .map(p -> {
                    var lista = filmesRepository.byPlaneta(p);
                    return new Planeta(p.id(), p.nome(), p.clima(), p.terreno(), lista.stream()
                            .map(d -> {
                                if (cache.getIfPresent(d.url()) == null) {
                                    cache.put(
                                            d.url(),
                                            new Filme(
                                                    d.titulo(),
                                                    d.episodio(),
                                                    d.abertura(),
                                                    d.estreia()));
                                }
                                return (Filme) cache.getIfPresent(d.url());
                            })
                            .toList());
                });
    }

    public Mono<Planeta> byName(String name) {
        return fetch.exec(String.format("https://swapi.dev/api/planets/?search=%s", name), RespostaDto.class)
                .map(res -> {
                    if (ListUtils.emptyIfNull(res.planetas()).isEmpty()) {
                        Mono.empty();
                    }

                    var p = res.planetas().getFirst();

                    var lista = filmesRepository.byPlaneta(p);
                    return new Planeta(p.id(), p.nome(), p.clima(), p.terreno(), lista.stream()
                            .map(d -> {
                                if (cache.getIfPresent(d.url()) == null) {
                                    cache.put(
                                            d.url(),
                                            new Filme(
                                                    d.titulo(),
                                                    d.episodio(),
                                                    d.abertura(),
                                                    d.estreia()));
                                }
                                return (Filme) cache.getIfPresent(d.url());
                            })
                            .toList());
                });
    }

}
