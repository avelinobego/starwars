package br.com.americanas.digital.starwars.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

public record FilmesDto(
        @JsonProperty("url") String url,
        @JsonProperty("title") String titulo,
        @JsonProperty("episode_id") Integer episodio,
        @JsonProperty("opening_crawl") String abertura,
        @JsonProperty("release_date") LocalDate estreia) {

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((url == null) ? 0 : url.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        FilmesDto other = (FilmesDto) obj;
        if (url == null) {
            if (other.url != null)
                return false;
        } else if (!url.equals(other.url))
            return false;
        return true;
    }

}
