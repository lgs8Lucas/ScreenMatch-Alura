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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getTotalSeason() {
        return totalSeason;
    }

    public void setTotalSeason(Integer totalSeason) {
        this.totalSeason = totalSeason;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public List<String> getActors() {
        return actors;
    }

    public void setActors(List<String> actors) {
        this.actors = actors;
    }

    public String getUrlPoster() {
        return urlPoster;
    }

    public void setUrlPoster(String urlPoster) {
        this.urlPoster = urlPoster;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    @Override
    public String toString() {
        return "title='" + title + '\'' +
                ", genre=" + genre +
                ", totalSeason=" + totalSeason +
                ", rating=" + rating +
                ", actors=" + actors +
                ", urlPoster='" + urlPoster + '\'' +
                ", plot='" + plot + '\'';
    }
}
