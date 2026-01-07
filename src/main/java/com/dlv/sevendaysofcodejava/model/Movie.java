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
        String year) implements Content, Comparable<Content> {

    @Override
    public int compareTo(Content other) {
        double thisRating = parseRating(this.rating);
        double otherRating = parseRating(other.rating());
        // Natural order: ascending by rating
        return Double.compare(thisRating, otherRating);
    }

    private double parseRating(String ratingStr) {
        if (ratingStr == null || ratingStr.isEmpty()) {
            return 0.0; // Assign a low value for null or empty ratings
        }
        try {
            // IMDB ratings are like "8.7", so Double is appropriate.
            return Double.parseDouble(ratingStr);
        } catch (NumberFormatException e) {
            // If parsing fails, assign a low value
            return 0.0;
        }
    }
}
