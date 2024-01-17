package br.com.americanas.digital.starwars.entities;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public record Planeta (
        @Id Long id,
        @TextIndexed String nome,
        String clima,
        String terreno,
        List<Filme> filmes) {

    @Transient
    public static final String SEQUENCE_NAME = "planets_sequence";

}
