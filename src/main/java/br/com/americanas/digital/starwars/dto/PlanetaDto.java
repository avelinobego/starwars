package br.com.americanas.digital.starwars.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.com.americanas.digital.starwars.utils.IdDeserializer;

public record PlanetaDto(
        @JsonProperty("url") @JsonDeserialize(using = IdDeserializer.class) Long id,
        @JsonProperty("name") String nome,
        @JsonProperty("climate") String clima,
        @JsonProperty("terrain") String terreno,
        @JsonProperty("films") List<String> filmes) {

};
