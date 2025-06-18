package br.com.alura.screenmatch.main;

import br.com.alura.screenmatch.models.EpisodeData;
import br.com.alura.screenmatch.models.SeasonData;
import br.com.alura.screenmatch.models.SeriesData;
import br.com.alura.screenmatch.services.APIConsumption;
import br.com.alura.screenmatch.services.ConvertData;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private Scanner sc = new Scanner(System.in);
    private APIConsumption api = new APIConsumption();
    private ConvertData convertData = new ConvertData();

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

        seasons.forEach( s -> {
            System.out.println("Season: "+ s.number());
            System.out.print("Episodes: [   ");
            s.episodes().forEach( e -> System.out.print(e.title() + "   "));
            System.out.println("]");
        });

    }
}
