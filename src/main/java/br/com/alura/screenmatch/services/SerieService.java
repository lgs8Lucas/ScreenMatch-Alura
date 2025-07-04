package br.com.alura.screenmatch.services;

import br.com.alura.screenmatch.dto.SerieDTO;
import br.com.alura.screenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SerieService {
    @Autowired
    private SerieRepository repository; // Para acesso ao banco de dados

    public List<SerieDTO> getAllSeries(){
        return repository.findAll().stream().map(SerieDTO::new).toList();
    }
}
