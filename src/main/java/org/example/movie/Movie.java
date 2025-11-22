package org.example.movie;

public class Movie {
    private final int id;
    private final String title;
    private final String genre;
    private final int year;
    private final String language;
    private final int durationMinutes;
    private final double averageRating;

    public Movie(int id, String title, String genre,
                 int year, String language,
                 int durationMinutes, double averageRating) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.year = year;
        this.language = language;
        this.durationMinutes = durationMinutes;
        this.averageRating = averageRating;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public int getYear() {
        return year;
    }

    public String getLanguage() {
        return language;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public double getAverageRating() {
        return averageRating;
    }

    @Override
    public String toString() {
        return title + " (" + year + ") - " + genre;
    }
}

