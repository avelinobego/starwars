package br.com.americanas.digital.starwars.repository;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.americanas.digital.starwars.entities.Filme;
import br.com.americanas.digital.starwars.entities.Planeta;

@SpringBootTest
public class PlanetasRepositoryTest {

    @Autowired
    private LocalPlanetasRepository repository;

    @Test
    public void insertPlanet() {
        Filme filme = new Filme(
                "Não tem!",
                0,
                "Nenhuma",
                LocalDate.now());
        Planeta planeta = new Planeta(
                1024L,
                "Terra",
                "ameno",
                "terra",
                List.of(filme));
        repository.save(planeta).block();
    }
}
