package br.com.alura.screenmatch.models;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Episode {
    private Integer season;
    private String title;
    private Integer number;
    private double rating;
    private LocalDate releaseDate;

    public Episode(Integer season, EpisodeData e) {
        this.season = season;
        this.title = e.title();
        this.number = e.number();
        try{
            this.rating = Double.valueOf(e.rating());
        } catch (NumberFormatException exception){
            this.rating = 0;
        }
        try{
            this.releaseDate = LocalDate.parse(e.releaseDate());
        } catch (DateTimeParseException exception){
            this.releaseDate = null;
        }
    }

    public Integer getSeason() {
        return season;
    }

    public void setSeason(Integer season) {
        this.season = season;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return "Episode: "+title+", T"+season+" E"+number+", Rating: "+rating+", Release date: "+releaseDate;
    }
}
