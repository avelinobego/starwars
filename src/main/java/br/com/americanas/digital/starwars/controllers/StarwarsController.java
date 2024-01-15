package br.com.americanas.digital.starwars.controllers;

import java.util.concurrent.ExecutionException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.americanas.digital.starwars.entities.Planeta;
import br.com.americanas.digital.starwars.service.PlanetasService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController("/planetas")
@ResponseBody
public class StarwarsController {

    private final PlanetasService planetasService;

    public StarwarsController(PlanetasService apiService) {
        this.planetasService = apiService;
    }

    @GetMapping(value = { "/planetas", "/planetas/" })
    public Flux<Planeta> all() {
        return planetasService.all();
    }

    @GetMapping(value = { "/planetas/{id}", "/planetas/{id}/" })
    public Mono<Planeta> byId(@PathVariable("id") Long id) {
        return planetasService.byId(id);
    }

    @GetMapping(value = { "/planetas/name/{name}", "/planetas/name/{name}/" })
    public Mono<Planeta> byName(@PathVariable("name") String name) {
        return planetasService.byName(name);
    }

    @PostMapping(value = { "/planetas/add", "/planetas/add/" })
    public Mono<Planeta> add(@RequestBody Planeta planeta) throws InterruptedException, ExecutionException {
        return planetasService.save(planeta);
    }

}
