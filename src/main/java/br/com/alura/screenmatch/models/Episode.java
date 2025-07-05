package br.com.alura.screenmatch.models;

import jakarta.persistence.*;
import org.hibernate.annotations.CascadeType;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Entity
@Table(name = "episodes")
public class Episode {
    private Integer season;
    private String title;
    private Integer number;
    private double rating;
    private LocalDate releaseDate;

    @ManyToOne
    private Series series;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    public Episode() {

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

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Series getSeries() {
        return series;
    }

    public void setSeries(Series series) {
        this.series = series;
    }


}
