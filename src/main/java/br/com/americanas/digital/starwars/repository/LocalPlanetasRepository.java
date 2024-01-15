package br.com.americanas.digital.starwars.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import br.com.americanas.digital.starwars.entities.Planeta;
import reactor.core.publisher.Mono;

public interface LocalPlanetasRepository extends ReactiveCrudRepository<Planeta, Long> {

    Mono<Planeta> nome(String name);

}
