package br.com.alura.screenmatch.repository;

import br.com.alura.screenmatch.models.Series;
import org.springframework.data.jpa.repository.JpaRepository;
//                                      Repositório de série, o id é Long
public interface SerieRepository extends JpaRepository<Series, Long> {

}
