package br.com.alura.screenmatch.controllers;

import br.com.alura.screenmatch.dto.SerieDTO;
import br.com.alura.screenmatch.services.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController // Define classe como controller
public class SeriesController {
    @Autowired
    private SerieService service; // Para acessar os services

    @GetMapping("/series") // Cria um get para /series
    public List<SerieDTO> getSeries() { // O /series chamará esta função
        return service.getAllSeries(); // Solicita dados ao service
    }
}
