package br.com.alura.screenmatch.dto;

import br.com.alura.screenmatch.models.Episode;

public record EpisodeDTO (String titulo, Integer numeroEpisodio, Integer temporada) {
    public EpisodeDTO(Episode episode) {
        this(
                episode.getTitle(),
                episode.getNumber(),
                episode.getSeason()
        );
    }
}
