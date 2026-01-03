package com.dlv.sevendaysofcodejava.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Movie(
        @JsonAlias("Title")
        String titulo,
        @JsonAlias("Poster")
        String urlImage,
        @JsonAlias("imdbRating")
        String note,
        @JsonAlias("Year")
        String ano) {
}
