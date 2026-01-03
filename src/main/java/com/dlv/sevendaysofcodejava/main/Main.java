package com.dlv.sevendaysofcodejava.main;

import com.dlv.sevendaysofcodejava.service.ConsumoAPI;

import java.util.List;

public class Main {

    private ConsumoAPI consumo;
    private final String ENDERECO = "https://www.omdbapi.com/";
    private final String API_KEY = "&apikey=6585022c";

    public Main() {
        this.consumo = new ConsumoAPI();
    }

    public void buscarPorId(List<String> ids) {
        ids.forEach(id -> {
            String url = ENDERECO + "?i=" + id + API_KEY;
            // System.out.println("Buscando dados para ID: " + id + " na URL: " + url);

            var json = consumo.obterDados(url);
            System.out.println("Dados obtidos: " + json);
        });
    }
}
