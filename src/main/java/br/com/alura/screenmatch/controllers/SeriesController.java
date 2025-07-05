package br.com.alura.screenmatch.controllers;

import br.com.alura.screenmatch.dto.SerieDTO;
import br.com.alura.screenmatch.dto.EpisodeDTO;
import br.com.alura.screenmatch.services.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RequestMapping("/series")
@RestController // Define classe como controller
public class SeriesController {
    @Autowired
    private SerieService service; // Para acessar os services

    @GetMapping // Cria um get para /series
    public List<SerieDTO> getSeries() { // O /series chamará esta função
        return service.getAllSeries(); // Solicita dados ao service
    }

    @GetMapping("/top5") // Fica /series/top5
    public List<SerieDTO> getTop5Series() {
        return service.getTop5Series();
    }

    @GetMapping("/lancamentos")
    public List<SerieDTO> getTop5ReleasedSeries() {
        return service.getTop5ReleasedSeries();
    }

    @GetMapping("/{id}") // Entre chaves para informar que é um parâmetro
    public SerieDTO getSerieById(@PathVariable Long id) { // Coleta
        return service.getSeriesById(id);
    }

    @GetMapping("/{id}/temporadas/todas")
    public List<EpisodeDTO> getAllSeasons(@PathVariable Long id){
        return service.getAllSeasons(id);
    }

    @GetMapping("/{serie}/temporadas/{season}")
    public List<EpisodeDTO> getSeason(@PathVariable Long serie, @PathVariable Integer season){
        return service.getSeason(serie, season);
    }

    @GetMapping("/categoria/{genre}")
    public List<SerieDTO> getSeriesByGenre(@PathVariable String genre){
        return service.getSeriesByGenre(genre);
    }

}
