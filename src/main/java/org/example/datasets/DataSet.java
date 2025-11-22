package org.example.datasets;

import org.example.movie.Movie;
import org.example.user.User;

import java.util.ArrayList;
import java.util.List;

public class DataSet {

    private final List<Movie> movies = new ArrayList<>();
    private final List<User> users = new ArrayList<>();

    public DataSet() {
        loadMovies();
        loadUsersWithRatings();
    }

    private void loadMovies() {
        movies.add(new Movie(1, "Accion Máxima", "Acción", 2022, "Español", 110, 4.5));
        movies.add(new Movie(2, "Amor en París", "Romance", 2020, "Inglés", 98, 4.1));
        movies.add(new Movie(3, "Risas a Medianoche", "Comedia", 2019, "Español", 95, 3.8));
        movies.add(new Movie(4, "Código Secreto", "Suspenso", 2021, "Inglés", 120, 4.7));
        movies.add(new Movie(5, "Espacio Profundo", "Ciencia Ficción", 2018, "Inglés", 130, 4.3));
        movies.add(new Movie(6, "Familia en Apuros", "Comedia", 2023, "Español", 100, 4.0));
        movies.add(new Movie(7, "Guerra y Honor", "Acción", 2019, "Inglés", 125, 4.2));
        movies.add(new Movie(8, "Lágrimas del Pasado", "Drama", 2020, "Español", 105, 4.4));
        movies.add(new Movie(9, "Universos Paralelos", "Ciencia Ficción", 2024, "Inglés", 140, 4.8));
        movies.add(new Movie(10, "Café y Recuerdos", "Drama", 2017, "Español", 90, 3.9));
    }

    private void loadUsersWithRatings() {
        User u1 = new User(1, "Ana");
        u1.addRating(1, 5.0);
        u1.addRating(3, 4.0);
        u1.addRating(5, 5.0);
        u1.addRating(8, 4.5);

        User u2 = new User(2, "Luis");
        u2.addRating(1, 4.5);
        u2.addRating(2, 4.0);
        u2.addRating(4, 5.0);
        u2.addRating(7, 4.0);

        User u3 = new User(3, "María");
        u3.addRating(2, 5.0);
        u3.addRating(3, 3.5);
        u3.addRating(6, 4.5);
        u3.addRating(10, 4.0);

        User u4 = new User(4, "Carlos");
        u4.addRating(4, 4.5);
        u4.addRating(5, 4.0);
        u4.addRating(7, 4.5);
        u4.addRating(9, 5.0);

        users.add(u1);
        users.add(u2);
        users.add(u3);
        users.add(u4);
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public List<User> getUsers() {
        return users;
    }

    public User findUserById(int id) {
        return users.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public Movie findMovieById(int id) {
        return movies.stream()
                .filter(m -> m.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
