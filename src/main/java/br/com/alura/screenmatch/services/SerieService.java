package br.com.alura.screenmatch.services;

import br.com.alura.screenmatch.dto.EpisodeDTO;
import br.com.alura.screenmatch.dto.SerieDTO;
import br.com.alura.screenmatch.models.Episode;
import br.com.alura.screenmatch.models.Genre;
import br.com.alura.screenmatch.models.Series;
import br.com.alura.screenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SerieService {
    @Autowired
    private SerieRepository repository; // Para acesso ao banco de dados

    private List<SerieDTO> convertSeriesToDTO(List<Series> series){
        return series.stream().map(SerieDTO::new).toList();
    }

    private List<EpisodeDTO> convertEpisodestoDTO(List<Episode> episodes){
        return episodes.stream().map(EpisodeDTO::new).toList();
    }

    public List<SerieDTO> getAllSeries(){
        return convertSeriesToDTO(repository.findAll());
    }

    public List<SerieDTO> getTop5Series() {
        return convertSeriesToDTO(repository.findTop5ByOrderByRatingDesc());
    }

    public List<SerieDTO> getTop5ReleasedSeries() {
        return convertSeriesToDTO(repository.top5LastSeries());
    }

    public SerieDTO getSeriesById(Long id) {
        var opt = repository.findById(id);
        return opt.map(SerieDTO::new).orElse(null); // Se achar retorna, senão, null
    }

    public List<EpisodeDTO> getAllSeasons(Long id) {
        var opt = repository.findById(id);
        return opt.map(series -> convertEpisodestoDTO(series.getEpisodes())).orElse(null);
    }


    public List<EpisodeDTO> getSeason(Long serie, Integer season) {
        return convertEpisodestoDTO(repository.episodesBySeriesAndSeason(serie, season));
    }

    public List<SerieDTO> getSeriesByGenre(String genre) {
        Genre g = Genre.fromPortuguese(genre);
        return convertSeriesToDTO(repository.findByGenre(g));
    }
}
