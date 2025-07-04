package br.com.alura.screenmatch.dto;

import br.com.alura.screenmatch.models.Genre;
import br.com.alura.screenmatch.models.Series;

public record SerieDTO(Long id, String titulo, Integer totalTemporadas, double avaliacao , Genre genero, String atores, String poster, String sinopse) {
    public SerieDTO(Series series) {
        this(series.getId(),
             series.getTitle(),
             series.getTotalSeason(),
             series.getRating(),
             series.getGenre(),
             series.getActors(),
             series.getUrlPoster(),
             series.getPortuguesePlot());
    }
}
