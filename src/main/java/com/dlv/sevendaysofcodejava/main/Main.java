package com.dlv.sevendaysofcodejava.main;

import com.dlv.sevendaysofcodejava.model.Content;
import com.dlv.sevendaysofcodejava.model.Movie;
import com.dlv.sevendaysofcodejava.service.HTMLGenerator;
import com.dlv.sevendaysofcodejava.service.ImdbApiClient;
import com.dlv.sevendaysofcodejava.service.ImdbMovieJsonParser;
import com.dlv.sevendaysofcodejava.service.JsonParser;
import io.github.cdimascio.dotenv.Dotenv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class Main {

    Logger logger = LoggerFactory.getLogger(Main.class);
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

        JsonParser parser = new ImdbMovieJsonParser(json);
        List<? extends Content> contentList = parser.parse();

        try (PrintWriter writer = new PrintWriter("movies.html")) {
            HTMLGenerator generator = new HTMLGenerator(writer);
            generator.generate(contentList);
            logger.info("HTML Gerado com secesso");
        } catch (IOException e) {
            logger.error("Erro ao gerar HTML: "+ e.getMessage());
        }
    }
}
