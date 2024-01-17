package br.com.americanas.digital.starwars.service;

import java.util.Comparator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.americanas.digital.starwars.entities.Planeta;
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
        var teste = Flux.fromStream(all.toStream().sorted(new Comparator<Planeta>(){

            @Override
            public int compare(Planeta arg0, Planeta arg1) {
                return arg0.id().compareTo(arg1.id());
            }
            
        }));
        teste.log().subscribe();
    }

    @Test
    void testById(){
        var teste = apiService.byId(6L);
        log.info(teste.log().block().toString());
    }

    @Test
    void testByName(){
        var teste = apiService.byName("Tatooine");
        log.info(teste.log().block().toString());
    }

}
