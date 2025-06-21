package br.com.alura.screenmatch.main;

import br.com.alura.screenmatch.models.SeasonData;
import br.com.alura.screenmatch.models.SeriesData;
import br.com.alura.screenmatch.services.APIConsumption;
import br.com.alura.screenmatch.services.ConvertData;

import java.util.*;

public class Main {
    private final Scanner sc = new Scanner(System.in);
    private final APIConsumption api = new APIConsumption();
    private final ConvertData convertData = new ConvertData();

    private final String URI = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=fe961640";

    private List<SeriesData> series = new ArrayList<SeriesData>();

    public void showMenu() {
        var option = 1;
        while (option != 0) {
            var menu = """
                1 - Search Series
                2 - Search Episodes
                3 - Show History
                
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
                    showHistory();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option");
            }
        }
    }

    private void showHistory() {
        series.forEach(System.out::println);
    }

    private SeriesData getSeriesData() {
        System.out.print("Type Series Name: ");
        String seriesName = sc.nextLine();
        var json = api.getData(URI + seriesName.replace(" ", "+")+API_KEY);
        return convertData.getData(json, SeriesData.class);
    }

    private void searchSeriesWeb(){
        SeriesData data = getSeriesData();
        series.add(data);
        System.out.println(data);
    }

    private void searchEpisodeForSerie() {
        SeriesData data = getSeriesData();
        List<SeasonData> seasons = new ArrayList<>();

        for (int i = 1; i <= data.totalSeason() ; i++) {
            var json = api.getData(URI + data.title().replace(" ", "+")+"&season="+i+API_KEY);
            seasons.add(convertData.getData(json, SeasonData.class));
        }
        seasons.forEach(System.out::println);
    }
}
