package com.dlv.sevendaysofcodejava.service;

import com.dlv.sevendaysofcodejava.model.Movie;

import java.io.PrintWriter;
import java.util.List;

public class HTMLGenerator {

    private final PrintWriter writer;

    public HTMLGenerator(PrintWriter writer) {
        this.writer = writer;
    }

    public void generate(List<Movie> movies) {
        writer.println(
                """
                <!DOCTYPE html>
                <html lang="pt-BR">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Top 250 Movies</title>
                    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css"
                        integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
                </head>
                <body>
                    <div class="container">
                        <div class="row">
                """
        );

        movies.forEach(movie -> {
            String movieTemplate =
                    """
                    <div class="col-md-4 mb-4">
                        <div class="card text-white bg-dark">
                            <h4 class="card-header">%s</h4>
                            <img class="card-img-top" src="%s" alt="%s">
                            <div class="card-body">
                                <p class="card-text">Nota: %s - Ano: %s</p>
                            </div>
                        </div>
                    </div>
                    """;
            writer.println(String.format(movieTemplate, movie.titulo(), movie.urlImage(), movie.titulo(), movie.note(), movie.ano()));
        });

        writer.println(
                """
                        </div>
                    </div>
                </body>
                </html>
                """
        );
    }
}
