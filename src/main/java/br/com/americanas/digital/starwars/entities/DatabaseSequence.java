package br.com.americanas.digital.starwars.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public record DatabaseSequence(
        @Id String id,
        Long seq) {

}
