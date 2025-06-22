package br.com.alura.screenmatch.models;

import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

public class Series {
    private String title;
    private Integer totalSeason;
    private double rating;
    private Genre genre;
    private List<String> actors;
    private String urlPoster;
    private String plot;

    public Series(SeriesData seriesData) {
        this.title = seriesData.title();
        this.totalSeason = seriesData.totalSeason();
        this.rating = OptionalDouble.of(Double.parseDouble(seriesData.rating())).orElse(0.0);
        this.actors = List.of(seriesData.actors().split(", "));
        this.urlPoster = seriesData.urlPoster();
        this.plot = seriesData.plot();
        this.genre = Genre.fromString(seriesData.genre().split(",")[0]);


    }
}
