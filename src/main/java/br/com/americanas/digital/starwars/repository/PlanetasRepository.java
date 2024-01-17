package br.com.americanas.digital.starwars.repository;

import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.Comparator;

import org.apache.commons.collections4.ListUtils;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

import br.com.americanas.digital.starwars.dto.FilmesDto;
import br.com.americanas.digital.starwars.dto.PlanetaDto;
import br.com.americanas.digital.starwars.dto.RespostaDto;
import br.com.americanas.digital.starwars.entities.Filme;
import br.com.americanas.digital.starwars.entities.Planeta;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class PlanetasRepository {

    private final Fetch fetch;
    private final FilmesRepository filmesRepository;

    public PlanetasRepository(
            WebClient webClient,
            FilmesRepository filmesRepository) {
        this.fetch = new Fetch(webClient);
        this.filmesRepository = filmesRepository;
    }

    public Flux<Planeta> all() {

        var fluxPlanetas = fetch.exec("https://swapi.dev/api/planets", RespostaDto.class)
                .expand(expander -> fetch.exec(expander.next(), RespostaDto.class))
                .flatMapIterable(r -> {
                    return r.planetas();
                })
                .flatMap(this::createFluxPlanetas);

        return fluxPlanetas;

    }

    private Flux<Planeta> createFluxPlanetas(PlanetaDto planetaDto) {
        var fluxPlanetaDto = Flux.just(planetaDto);

        var fluxFilmeDto = Flux.fromIterable(planetaDto.filmes())
                .flatMap(url -> fetch.exec(url, FilmesDto.class))
                .cache(Duration.ofMinutes(5))
                .collectList();

        var result = fluxPlanetaDto.zipWith(fluxFilmeDto, (p, f) -> {
            var filmes = f.stream().map(fdto -> new Filme(
                    fdto.titulo(),
                    fdto.episodio(),
                    fdto.abertura(),
                    fdto.estreia()))
                    .toList();
            return new Planeta(p.id(), p.nome(), p.clima(), p.terreno(), filmes);
        });

        return result;
    }

    public Mono<Planeta> byId(Long id) {
        return fetch.exec(String.format("https://swapi.dev/api/planets/%d/", id), PlanetaDto.class)
                .flatMap(d -> Mono.from(createFluxPlanetas(d)));
    }

    public Mono<Planeta> byName(String name) {
        return fetch.exec(String.format("https://swapi.dev/api/planets/?search=%s", name), RespostaDto.class)
                .map(resp -> resp.planetas().getFirst())
                .flatMap(d -> Mono.from(createFluxPlanetas(d)));
    }

}
