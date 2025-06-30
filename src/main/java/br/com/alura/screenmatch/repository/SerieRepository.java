package br.com.alura.screenmatch.repository;

import br.com.alura.screenmatch.models.Genre;
import br.com.alura.screenmatch.models.Series;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

//                                      Repositório de série, o id é Long
public interface SerieRepository extends JpaRepository<Series, Long> {
    //                Busca pelo título ignorando case
    Optional<Series> findByTitleContainingIgnoreCase(String seriesName);

    List<Series> findByActorsContainingIgnoreCaseAndRatingGreaterThanEqual(String actor, double rating);

    List<Series> findTop5ByOrderByRatingDesc();

    List<Series> findByGenre(Genre genre);
}
