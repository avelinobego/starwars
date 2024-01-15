package br.com.americanas.digital.starwars.service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.aot.generate.GeneratedClass;
import org.springframework.stereotype.Service;

import br.com.americanas.digital.starwars.entities.Filme;
import br.com.americanas.digital.starwars.entities.Planeta;
import br.com.americanas.digital.starwars.repository.PlanetasRepository;
import br.com.americanas.digital.starwars.repository.LocalPlanetasRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PlanetasService {

    private final PlanetasRepository apiRepository;
    private final LocalPlanetasRepository planetasRepository;
    private final SequenceService sequenceService;

    public PlanetasService(
            PlanetasRepository apiRepository,
            LocalPlanetasRepository planetasRepository,
            SequenceService sequenceService) {
        this.apiRepository = apiRepository;
        this.planetasRepository = planetasRepository;
        this.sequenceService = sequenceService;
    }

    public Filme filme(String url) {
        return null;
    }

    public Flux<Planeta> all() {
        return Flux.concat(planetasRepository.findAll(), apiRepository.all());
    }

    public Mono<Planeta> byId(Long id) {
        return planetasRepository.findById(id).switchIfEmpty(apiRepository.byId(id)).log();
    }

    public Mono<Planeta> byName(String name) {
        return planetasRepository.nome(name).switchIfEmpty(apiRepository.byName(name)).log();
    }

    public Mono<Planeta> save(final Planeta planeta) throws InterruptedException, ExecutionException {

        var byName = CompletableFuture.supplyAsync(() -> planetasRepository.nome(planeta.nome()).block());
        var p = byName.get();

        var id = 0L;
        if (p == null) {
            id = sequenceService.generateSequence(Planeta.SEQUENCE_NAME);
        } else {
            id = p.id();
        }

        var result = new Planeta(
                id,
                planeta.nome(),
                planeta.clima(),
                planeta.terreno(),
                planeta.filmes());

        var mono = planetasRepository.save(result);
        return mono;
    }

}
