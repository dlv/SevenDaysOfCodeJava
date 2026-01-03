package com.dlv.sevendaysofcodejava.main;

import com.dlv.sevendaysofcodejava.service.ConsumoAPI;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private final ConsumoAPI consumo;
    private String endereco;
    private final String apiKey;

    public Main() {
        String endereco1;
        this.consumo = new ConsumoAPI();

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
        ids.forEach(id -> {
            String url = this.endereco + "?i=" + id + "&apikey=" + this.apiKey;
            // System.out.println("Buscando dados para ID: " + id + " na URL: " + url);

            String json = consumo.obterDados(url);
            // System.out.println("Dados obtidos: " + json);
            // Extract Title of the String json
            Matcher matcher = Pattern.compile("\"Title\":\"(.*?)\"").matcher(json);
            if (matcher.find()) {
                String title = matcher.group(1);
                System.out.println("Título: " + title);
            }

            // Extract Poster URL of the String json
            matcher = Pattern.compile("\"Poster\":\"(.*?)\"").matcher(json);
            if (matcher.find()) {
                String poster = matcher.group(1);
                System.out.println("Poster: " + poster);
            }
            System.out.println("\n-------------------------------------------------------------");
        });
    }
}
