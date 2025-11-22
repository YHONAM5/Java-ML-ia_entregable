package org.example;

import org.example.datasets.DataSet;
import org.example.movie.Movie;
import org.example.user.User;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void main(String[] args) {
        DataSet dataSet = new DataSet();

        System.out.println("=== Películas disponibles ===");
        dataSet.getMovies().forEach(System.out::println);

        System.out.println("\n=== Usuarios y sus valoraciones ===");
        for (User u : dataSet.getUsers()) {
            System.out.println("Usuario: " + u.getName());
            u.getRatings().forEach((movieId, rating) -> {
                Movie m = dataSet.findMovieById(movieId);
                System.out.println("  - " + m.getTitle() + " -> " + rating);
            });
        }
    }
}
