package org.example;

import org.example.datasets.DataSet;
import org.example.ml_model.MovieRecommender;
import org.example.movie.Movie;
import org.example.user.User;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void main(String[] args) {
        DataSet dataSet = new DataSet();

        System.out.println("=== Películas disponibles ===");
        dataSet.getMovies().forEach(System.out::println);

        User target = dataSet.findUserById(1); // por ejemplo, Ana
        System.out.println("\nGenerando recomendaciones para: " + target.getName());

        MovieRecommender recommender = new MovieRecommender(dataSet);
        var recomendaciones = recommender.recommendForUser(target, 5);

        System.out.println("\n=== Recomendaciones para " + target.getName() + " ===");
        for (Movie m : recomendaciones) {
            System.out.println("- " + m);
        }
    }
}
