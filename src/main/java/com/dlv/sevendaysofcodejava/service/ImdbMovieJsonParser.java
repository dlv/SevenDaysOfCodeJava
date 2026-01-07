package com.dlv.sevendaysofcodejava.service;

import com.dlv.sevendaysofcodejava.model.Movie;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class ImdbMovieJsonParser implements JsonParser {

    private String json;

    public ImdbMovieJsonParser(String json) {
        this.json = json;
    }

    public List<Movie> parse() {
        try {
            var mapper = new ObjectMapper();
            List<Movie> movies = mapper.readValue(json, new TypeReference<List<Movie>>() {});
            return movies;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
