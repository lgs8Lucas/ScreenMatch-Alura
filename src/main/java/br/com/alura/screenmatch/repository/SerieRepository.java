package br.com.alura.screenmatch.repository;

import br.com.alura.screenmatch.models.Episode;
import br.com.alura.screenmatch.models.Genre;
import br.com.alura.screenmatch.models.Series;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

//                                      Repositório de série, o id é Long
public interface SerieRepository extends JpaRepository<Series, Long> {
    //                Busca pelo título ignorando case
    Optional<Series> findByTitleContainingIgnoreCase(String seriesName);

    List<Series> findByActorsContainingIgnoreCaseAndRatingGreaterThanEqual(String actor, double rating);

    List<Series> findTop5ByOrderByRatingDesc();

    List<Series> findByGenre(Genre genre);

    List<Series> findByTotalSeasonLessThanEqualAndRatingGreaterThanEqual(int totalSeason, double rating);

    @Query("SELECT s FROM Series s WHERE s.totalSeason <= :totalSeason AND s.rating >= :rating")
    List<Series> seriesBySeasonsAndRating(Integer totalSeason, Double rating);

    @Query("SELECT e FROM Series s JOIN s.episodes e WHERE e.title ILIKE %:excerptEpisode%")
    List<Episode> episodesByExcerpt(String excerptEpisode);

    @Query("SELECT e FROM Series s JOIN s.episodes e WHERE s = :data ORDER BY e.rating DESC LIMIT 5")
    List<Episode> topEpisodesForSeries(Series data);

    @Query("SELECT e FROM Series s JOIN s.episodes e WHERE s = :data AND YEAR(e.releaseDate) >= :startYear")
    List<Episode> episodesBySeriesAndYear(Series data, int startYear);

    @Query("SELECT s FROM Series s " +
            "JOIN s.episodes e " +
            "GROUP BY s " +
            "ORDER BY MAX(e.releaseDate) DESC LIMIT 5")
    List<Series> top5LastSeries(); // Pega últimos lançamentos
}
