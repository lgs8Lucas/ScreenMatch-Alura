package br.com.alura.screenmatch.main;

import br.com.alura.screenmatch.models.*;
import br.com.alura.screenmatch.repository.SerieRepository;
import br.com.alura.screenmatch.services.APIConsumption;
import br.com.alura.screenmatch.services.ConvertData;

import java.util.*;

public class Main {
    private final Scanner sc = new Scanner(System.in);
    private final APIConsumption api = new APIConsumption();
    private final ConvertData convertData = new ConvertData();

    private final String URI = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey="+ System.getenv("OMDB_KEY");

    SerieRepository repository;

    public Main(SerieRepository repository) {
        this.repository = repository;
    }
    private List<Series> series = new ArrayList<>();
    private Optional<Series> searchedSerie = Optional.empty();


    public void showMenu() {
        var option = 1;
        while (true) {
            var menu = """
                1 - Search Series
                2 - Search Episodes
                3 - List All Series
                4 - Search Series By Title
                5 - Search Series By Actor
                6 - Top 5 Series
                7 - Search By Portuguese Genre
                8 - Search By Number Of Seasons and Rating
                9 - Search Episode By Excerpt
                10 - Top 5 episodes of the series
                11 - Search Episodes By Date
                
                0 - Exit
                """;
            System.out.println(menu);
            option = sc.nextInt();
            sc.nextLine();

            switch (option) {
                case 1:
                    searchSeriesWeb();
                    break;
                case 2:
                    searchEpisodeForSerie();
                    break;
                case 3:
                    listAllSeries();
                    break;
                case 4:
                    searchSeriesByTitle();
                    break;
                case 5:
                    searchSeriesByActor();
                    break;
                case 6:
                    searchTop5Series();
                    break;
                case 7:
                    searchSeriesByGenre();
                    break;
                case 8:
                    searchSeriesByTotalSeasonsAndRating();
                    break;
                case 9:
                    searchEpisodeByExcerpt();
                    break;
                case 10:
                    top5EpisodesForSries();
                    break;
                case 11:
                    searchEpisodesByDate();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option");
            }
        }
    }

    private void listAllSeries() {
        series = repository.findAll();
        series.stream()
                .sorted(Comparator.comparing(Series::getGenre))
                .forEach(System.out::println);
    }

    private SeriesData getSeriesData() {
        System.out.print("Type Series Name: ");
        String seriesName = sc.nextLine();
        var json = api.getData(URI + seriesName.replace(" ", "+")+API_KEY);
        return convertData.getData(json, SeriesData.class);
    }

    private void searchSeriesWeb(){
        Series data = new Series(getSeriesData());
        repository.save(data);
        System.out.println(data);
    }

    private void searchEpisodeForSerie() {
        listAllSeries();
        System.out.print("Type Series Name: ");
        String seriesName = sc.nextLine();

        Optional<Series> searchedSerie = repository.findByTitleContainingIgnoreCase(seriesName);
        if (searchedSerie.isPresent()) {
            var data = searchedSerie.get();
            List<SeasonData> seasons = new ArrayList<>();
            for (int i = 1; i <= data.getTotalSeason() ; i++) {
                var json = api.getData(URI + data.getTitle().replace(" ", "+")+"&season="+i+API_KEY);
                seasons.add(convertData.getData(json, SeasonData.class));
            }
            seasons.forEach(System.out::println);

            List<Episode> episodes = seasons.stream()
                    .flatMap(s -> s.episodes().stream()
                            .map(e -> new Episode(s.number(), e)))
                    .toList();

            data.setEpisodes(episodes); // Alterando localmente
            repository.save(data);

            return;
        }
        System.out.println("Series Not Found");
    }

    private void searchSeriesByTitle() {
        System.out.print("Type Series Name: ");
        String seriesName = sc.nextLine();
        searchedSerie = repository.findByTitleContainingIgnoreCase(seriesName);
        if (searchedSerie.isPresent()) {
            System.out.println("Series data: " + searchedSerie.get());
        } else {
            System.out.println("Series Not Found");
        }
    }

    private void searchSeriesByActor() {
        System.out.print("Type Actor Name: ");
        String actor = sc.nextLine();
        System.out.print("Type min rating: ");
        double rating = sc.nextDouble();
        sc.nextLine();
        List<Series> SeriesFounded = repository.findByActorsContainingIgnoreCaseAndRatingGreaterThanEqual(actor, rating);
        if (SeriesFounded.isEmpty()) {
            System.out.println("Series Not Found");
        } else  {
            System.out.println("Series with "+ actor+" With rating better than "+rating);
            SeriesFounded.forEach(s->{
                System.out.println(s.getTitle() + " - " + s.getGenre() + " | " + s.getRating());
            });
        }
    }

    private void searchTop5Series() {
        List<Series> top5 = repository.findTop5ByOrderByRatingDesc();
        if (top5.isEmpty()) {
            System.out.println("Series Not Found");
        }else {
            System.out.println("Top 5 Series:");
            top5.forEach(s->{
                System.out.println(s.getTitle() + " - " + s.getGenre() + " | " + s.getRating());
            });
        }
    }

    private void searchSeriesByGenre() {
        System.out.print("Type Genre: ");
        String ptGenre = sc.nextLine();
        Genre genre = Genre.fromPortuguese(ptGenre);
        List<Series> seriesFounded = repository.findByGenre(genre);
        if (seriesFounded.isEmpty()) {
            System.out.println("Series Not Found");
            return;
        }
        System.out.println("Series of portuguese genre "+ptGenre);
        seriesFounded.forEach(s->{
            System.out.println(s.getTitle() + " - " + s.getGenre() + " | " + s.getRating());
        });
    }

    private void searchSeriesByTotalSeasonsAndRating() {
        System.out.print("Type max number of seasons: ");
        Integer maxSeasons = sc.nextInt();
        sc.nextLine();
        System.out.print("Type min rating: ");
        Double minRating = sc.nextDouble();
        sc.nextLine();
        List<Series> seriesFounded = repository.seriesBySeasonsAndRating(maxSeasons, minRating);
        if (seriesFounded.isEmpty()) {
            System.out.println("Series Not Found");
            return;
        }
        System.out.println("Series: ");
        seriesFounded.forEach(s->{
            System.out.println(s.getTitle() + " - " + s.getGenre() + " | " + s.getRating());
        });
    }

    private void searchEpisodeByExcerpt(){
        System.out.print("Type Episode Name: ");
        String excerptEpisode = sc.nextLine();
        List<Episode> episodes = repository.episodesByExcerpt(excerptEpisode);
        if (episodes.isEmpty()) {
            System.out.println("Episode Not Found");
            return;
        }
        System.out.println("Episodes: ");
        episodes.forEach(e->{
            System.out.printf("Series: %s  Season %s  -  Episode %s  -  %s\n",
                    e.getSeries().getTitle(), e.getSeason(), e.getNumber(), e.getTitle());
        });
        System.out.println("Type Series Name: ");
    }

    private void top5EpisodesForSries(){
        searchSeriesByTitle();
        if (searchedSerie.isPresent()) {
            var data = searchedSerie.get();
            List<Episode> episodes = data.getEpisodes();
            if (episodes.isEmpty()) {
                System.out.println("No episodes found for this series");
                return;
            }
            List<Episode> top5 = repository.topEpisodesForSeries(data);
            System.out.println("Top 5 Episodes for "+data.getTitle()+": ");
            top5.forEach(e->{
                System.out.printf("Season %s  -  Episode %s  -  %s  |  Rating: %s\n",
                        e.getSeason(), e.getNumber(), e.getTitle(), e.getRating());
            });
        }
    }

    private void searchEpisodesByDate(){
        searchSeriesByTitle();
        if (searchedSerie.isPresent()) {
            var data = searchedSerie.get();
            if (data.getEpisodes().isEmpty()) {
                System.out.println("No episodes found for this series");
                return;
            }
            System.out.print("Type limit year: ");
            int limitYear = sc.nextInt();
            sc.nextLine();

            List<Episode> episodes = repository.episodesBySeriesAndYear(data, limitYear);
            if (episodes.isEmpty()) {
                System.out.println("Episodes not found for this series in the year "+limitYear+" or upper");
                return;
            }
            System.out.println("Episodes:");
            episodes.forEach(e->{
                System.out.printf("Season %s  -  Episode %s  -  %s  |  Rating: %s  -  %s\n",
                        e.getSeason(), e.getNumber(), e.getTitle(), e.getRating(), e.getReleaseDate().getYear());
            });
        }
    }
}
