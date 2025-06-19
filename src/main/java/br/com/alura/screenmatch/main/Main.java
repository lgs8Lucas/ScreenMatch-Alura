package br.com.alura.screenmatch.main;

import br.com.alura.screenmatch.models.Episode;
import br.com.alura.screenmatch.models.EpisodeData;
import br.com.alura.screenmatch.models.SeasonData;
import br.com.alura.screenmatch.models.SeriesData;
import br.com.alura.screenmatch.services.APIConsumption;
import br.com.alura.screenmatch.services.ConvertData;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private final Scanner sc = new Scanner(System.in);
    private final APIConsumption api = new APIConsumption();
    private final ConvertData convertData = new ConvertData();

    private final String URI = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=fe961640";

    public void showMenu() {
        System.out.println("Welcome to Screen Match");
        System.out.print("Write a series name to search: ");
        String seriesName = sc.nextLine();

        var json = api.getData(URI + seriesName.replace(" ", "+") + API_KEY);
        SeriesData series = convertData.getData(json, SeriesData.class);

        List<SeasonData> seasons = new ArrayList<>();

        System.out.println("Series: " + series.title());

        for (int i = 1; i <= series.totalSeason(); i++) {
            json = api.getData(URI + seriesName.replace(" ", "+") + "&season=" + i + API_KEY);
            SeasonData season = convertData.getData(json, SeasonData.class);
            seasons.add(season);
        }

        seasons.forEach(s -> {
            System.out.println("Season: " + s.number());
            System.out.print("Episodes: [   ");
            s.episodes().forEach(e -> System.out.print(e.title() + "   "));
            System.out.println("]");
        });
        //                                          Percorre a temporada e pega os episódios
        List<Episode> episodes = seasons.stream().flatMap(s -> s.episodes().stream()
                .map(e -> new Episode(s.number(), e))
        ).collect(Collectors.toList());

        System.out.println("\nTop 5 episodes:");
        episodes.stream()
                .filter(e -> e.getRating() != 0)
                .sorted(Comparator.comparing(Episode::getRating).reversed())
                .limit(5)
                .forEach(System.out::println);

        System.out.println("From what year do you want to see the episodes? ");
        var year = sc.nextInt();
        sc.nextLine();

        LocalDate searchDate = LocalDate.of(year, 1, 1);

        DateTimeFormatter  formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        System.out.print("Episodes after "+ year+": ");
        episodes.stream()
                .filter(e -> e.getReleaseDate() != null &&  e.getReleaseDate().isAfter(searchDate))
                .forEach(e -> System.out.println(
                        "T"+ e.getSeason() +
                                " E"+e.getNumber()+
                                " Release Date: " + e.getReleaseDate().format(formatter)
                ));


    }
}
