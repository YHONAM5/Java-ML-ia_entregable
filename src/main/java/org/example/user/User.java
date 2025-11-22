package org.example.user;

import java.util.HashMap;
import java.util.Map;

public class User {
    private final int id;
    private final String name;
    private final Map<Integer, Double> ratings = new HashMap<>();

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Map<Integer, Double> getRatings() {
        return ratings;
    }

    public void addRating(int movieId, double rating) {
        ratings.put(movieId, rating);
    }
}
