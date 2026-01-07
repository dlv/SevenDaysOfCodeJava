package com.dlv.sevendaysofcodejava.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Movie(
        @JsonAlias("Title")
        String title,
        @JsonAlias("Poster")
        String urlImage,
        @JsonAlias("imdbRating")
        String rating,
        @JsonAlias("Year")
        String year) implements Content {
}
