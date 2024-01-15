package br.com.americanas.digital.starwars.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.americanas.digital.starwars.dto.PlanetaDto;

public class PlanetasTest {

    @Test
    public void testParser() throws JsonMappingException, JsonProcessingException {
        var dados = """
                {
                    "name": "Tatooine",
                    "rotation_period": "23",
                    "orbital_period": "304",
                    "diameter": "10465",
                    "climate": "arid",
                    "gravity": "1 standard",
                    "terrain": "desert",
                    "surface_water": "1",
                    "population": "200000",
                    "residents": [
                        "https://swapi.dev/api/people/1/",
                        "https://swapi.dev/api/people/2/",
                        "https://swapi.dev/api/people/4/",
                        "https://swapi.dev/api/people/6/",
                        "https://swapi.dev/api/people/7/",
                        "https://swapi.dev/api/people/8/",
                        "https://swapi.dev/api/people/9/",
                        "https://swapi.dev/api/people/11/",
                        "https://swapi.dev/api/people/43/",
                        "https://swapi.dev/api/people/62/"
                    ],
                    "films": [
                        "https://swapi.dev/api/films/1/",
                        "https://swapi.dev/api/films/3/",
                        "https://swapi.dev/api/films/4/",
                        "https://swapi.dev/api/films/5/",
                        "https://swapi.dev/api/films/6/"
                    ],
                    "created": "2014-12-09T13:50:49.641000Z",
                    "edited": "2014-12-20T20:58:18.411000Z",
                    "url": "https://swapi.dev/api/planets/1/"
                }""";

        var mapper = new ObjectMapper().configure(
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                false);
        PlanetaDto instancia = mapper.readerFor(PlanetaDto.class)
                .readValue(dados);

        assertEquals(1, instancia.id());

    }
}
