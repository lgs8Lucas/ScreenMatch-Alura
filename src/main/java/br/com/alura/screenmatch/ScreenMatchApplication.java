package br.com.alura.screenmatch;

import br.com.alura.screenmatch.models.SeriesData;
import br.com.alura.screenmatch.services.APIConsumption;
import br.com.alura.screenmatch.services.ConvertData;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenMatchApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ScreenMatchApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        var consumptionApi = new APIConsumption();
        var convertData = new ConvertData();
        var json = consumptionApi.getData("https://www.omdbapi.com/?t=peaky+blinders&apikey=fe961640");
        SeriesData series = convertData.getData(json, SeriesData.class);
        System.out.println(series);
    }
}
