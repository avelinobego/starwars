package br.com.americanas.digital.starwars.entities;

import java.time.LocalDate;

public record Filme(
        String titulo,
        Integer episodio,
        String abertura,
        LocalDate estreia) {
}
