package br.com.americanas.digital.starwars.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SwapiCallerServiceTest {

    @Autowired
    private PlanetasService callerService;

    @Test
    void testPlanetas() {
        var planetas = callerService.all();
        planetas.subscribe(e -> System.out.println("************************ " + e.id()));
    }

    @Test
    void testUmPlaneta() {
        var planeta = callerService.byId(12L);
        planeta.subscribe(e -> System.out.println(e.terreno()));
    }

    @Test
    void testUmPlanetaPorNome() {
        var planeta = callerService.byName("xpto");
        planeta.subscribe(e -> System.out.println(e.terreno()));
    }

}
