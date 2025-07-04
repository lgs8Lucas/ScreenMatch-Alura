package br.com.alura.screenmatch.controllers;

import br.com.alura.screenmatch.dto.SerieDTO;
import br.com.alura.screenmatch.models.Series;
import br.com.alura.screenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController // Define classe como controller
public class SeriesController {
    @Autowired
    private SerieRepository repository;

    @GetMapping("/series") // Cria um get para /series
    public List<SerieDTO> getSeries() { // O /series chamará esta função
        return repository.findAll().stream().map(SerieDTO::new).toList();
    }
}
