package com.dlv.sevendaysofcodejava.main;

import com.dlv.sevendaysofcodejava.model.Movie;
import com.dlv.sevendaysofcodejava.service.HTMLGenerator;
import com.dlv.sevendaysofcodejava.service.ImdbApiClient;
import com.dlv.sevendaysofcodejava.service.ImdbMovieJsonParser;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class Main {

    private final String apiKey;
    private String endereco;

    public Main() {
        Dotenv dotenv = Dotenv.load();
        this.apiKey = dotenv.get("API_KEY");

        Properties prop = new Properties();
        try (InputStream input = Main.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find application.properties");
                this.endereco = "";
                return;
            }
            prop.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
            this.endereco  = "";
            return;
        }
        this.endereco =  prop.getProperty("api.address");
    }


    public void buscarPorId(List<String> ids) {
        ImdbApiClient imdbApiClient = new ImdbApiClient(apiKey, endereco);

        List<String> jsonMovies = ids.stream()
                .map(imdbApiClient::getBody)
                .collect(Collectors.toList());

        String json = "[" + String.join(",", jsonMovies) + "]";

        List<Movie> movies = new ImdbMovieJsonParser(json).parse();

        try (PrintWriter writer = new PrintWriter("movies.html")) {
            HTMLGenerator generator = new HTMLGenerator(writer);
            generator.generate(movies);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
