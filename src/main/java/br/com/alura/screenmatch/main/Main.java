package br.com.alura.screenmatch.main;

import br.com.alura.screenmatch.models.Episode;
import br.com.alura.screenmatch.models.SeasonData;
import br.com.alura.screenmatch.models.Series;
import br.com.alura.screenmatch.models.SeriesData;
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

    public void showMenu() {
        var option = 1;
        while (true) {
            var menu = """
                1 - Search Series
                2 - Search Episodes
                3 - List All Series
                4 - Search Series By Title
                5 - Search Series By Actor
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
        Optional<Series> searchedSerie = repository.findByTitleContainingIgnoreCase(seriesName);
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
        List<Series> SeriesFounded = repository.findByActorsContainingIgnoreCaseAndRatingAfter(actor, rating);
        if (SeriesFounded.isEmpty()) {
            System.out.println("Series Not Found");
        } else  {
            System.out.println("Series with "+ actor+" With rating better than "+rating);
            SeriesFounded.forEach(s->{
                System.out.println(s.getTitle() + " - " + s.getGenre() + " | " + s.getRating());
            });
        }
    }
}
