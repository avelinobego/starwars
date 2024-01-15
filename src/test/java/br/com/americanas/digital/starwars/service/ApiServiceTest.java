package br.com.americanas.digital.starwars.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@SpringBootTest
@Slf4j
public class ApiServiceTest {

    @Autowired
    private PlanetasService apiService;

    @Test
    void testAll() {
        var all = apiService.all();
        var teste = Flux.fromStream(all.toStream());
        teste.log().subscribe();
    }

    @Test
    void testById(){
        var teste = apiService.byId(1L);
        log.info(teste.log().block().toString());
    }

    @Test
    void testByName(){
        var teste = apiService.byName("Tatooine");
        log.info(teste.log().block().toString());
    }

}
