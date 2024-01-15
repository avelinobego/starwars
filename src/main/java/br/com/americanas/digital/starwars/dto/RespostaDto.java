package br.com.americanas.digital.starwars.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RespostaDto(
        @JsonProperty("next") String next,
        @JsonProperty("results") List<PlanetaDto> planetas) {
}
