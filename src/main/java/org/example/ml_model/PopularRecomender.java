package org.example.ml_model;

import org.example.datasets.DataSet;
import org.example.movie.Movie;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PopularRecomender {
    private final DataSet dataSet;

    public PopularRecomender(DataSet dataSet){
        this.dataSet = dataSet;
    }

    //Recomendacion de peliculas con mejor promedio

    public List<Movie> getTopPopularMovies(int max){
        return dataSet.getMovies().stream().sorted(Comparator.comparingDouble(Movie::getAverageRating).reversed()).limit(max).collect(Collectors.toList());
    }

}
