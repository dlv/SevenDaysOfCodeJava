package com.dlv.sevendaysofcodejava.main;

import com.dlv.sevendaysofcodejava.model.Movie;
import com.dlv.sevendaysofcodejava.service.ConsumoAPI;
import com.dlv.sevendaysofcodejava.service.ConverteDados;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private final ConsumoAPI consumo;
    private ConverteDados conversor;
    private String endereco;
    private final String apiKey;

    public Main() {
        this.consumo = new ConsumoAPI();
        this.conversor = new ConverteDados();

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
        List<Movie> movies = ids.stream().map(id -> {
            String url = this.endereco + "?i=" + id + "&apikey=" + this.apiKey;
            // System.out.println("Buscando dados para ID: " + id + " na URL: " + url);

            String json = consumo.obterDados(url);
            Movie movie = conversor.obterDados(json, Movie.class);
            return movie;
        }).toList();

        movies.forEach(System.out::println);

        /*ids.forEach(id -> {
            String url = this.endereco + "?i=" + id + "&apikey=" + this.apiKey;
            // System.out.println("Buscando dados para ID: " + id + " na URL: " + url);

            String json = consumo.obterDados(url);

        });*/
    }
}
