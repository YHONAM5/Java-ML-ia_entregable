package org.example.ml_model;


import org.example.datasets.DataSet;
import org.example.movie.Movie;
import org.example.user.User;
import weka.classifiers.trees.J48;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;

import java.util.*;
import java.util.stream.Collectors;

public class MovieRecommender {

    private final DataSet dataSet;
    private Instances trainingData;
    private J48 tree;
    private Map<String, Integer> genreIndexMap;

    public MovieRecommender(DataSet dataSet) {
        this.dataSet = dataSet;
        buildGenreIndexMap();
        buildTrainingData();
        trainModel();
    }

    private void buildGenreIndexMap() {
        Set<String> genres = dataSet.getMovies().stream().map(Movie::getGenre).collect(Collectors.toSet());

        genreIndexMap = new HashMap<>();
        int i = 0;
        for (String g : genres) {
            genreIndexMap.put(g, i++);
        }
    }

    private void buildTrainingData() {
        ArrayList<Attribute> attributes = new ArrayList<>();

        Attribute attUserId = new Attribute("userId");

        ArrayList<String> genreValues = new ArrayList<>(genreIndexMap.keySet());
        Attribute attGenre = new Attribute("genre", genreValues);

        Attribute attDuration = new Attribute("durationMinutes");
        Attribute attAvgRating = new Attribute("averageRating");

        ArrayList<String> classValues = new ArrayList<>();
        classValues.add("si");
        classValues.add("no");
        Attribute attLike = new Attribute("like", classValues);

        attributes.add(attUserId);
        attributes.add(attGenre);
        attributes.add(attDuration);
        attributes.add(attAvgRating);
        attributes.add(attLike);

        trainingData = new Instances("MoviePreference", attributes, 0);
        trainingData.setClassIndex(trainingData.numAttributes() - 1);

        for (User u : dataSet.getUsers()) {
            for (Map.Entry<Integer, Double> entry : u.getRatings().entrySet()) {
                int movieId = entry.getKey();
                double rating = entry.getValue();
                Movie m = dataSet.findMovieById(movieId);
                if (m == null) continue;

                String likeLabel = rating >= 4.0 ? "si" : "no";

                double[] values = new double[trainingData.numAttributes()];
                values[0] = u.getId();
                values[1] = genreValues.indexOf(m.getGenre());
                values[2] = m.getDurationMinutes();
                values[3] = m.getAverageRating();
                values[4] = trainingData.classAttribute().indexOfValue(likeLabel);

                trainingData.add(new DenseInstance(1.0, values));
            }
        }
    }

    private void trainModel() {
        try {
            tree = new J48();
            tree.buildClassifier(trainingData);
            System.out.println("Árbol de decisión entrenado:");
            System.out.println(tree);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Movie> recommendForUser(User targetUser, int maxRecommendations) {
        List<Movie> allMovies = dataSet.getMovies();
        Set<Integer> seenMovies = targetUser.getRatings().keySet();

        List<MovieScore> scoredMovies = new ArrayList<>();

        for (Movie m : allMovies) {
            if (seenMovies.contains(m.getId())) {
                // ya la vio, no la recomendamos de nuevo
                continue;
            }

            try {
                double score = predictLikeProbability(targetUser, m);
                scoredMovies.add(new MovieScore(m, score));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // ordenar por probabilidad de gustar
        scoredMovies.sort((a, b) -> Double.compare(b.score, a.score));

        return scoredMovies.stream()
                .limit(maxRecommendations)
                .map(ms -> ms.movie)
                .collect(Collectors.toList());
    }

    private double predictLikeProbability(User user, Movie movie) throws Exception {
        DenseInstance instance = new DenseInstance(trainingData.numAttributes());
        instance.setDataset(trainingData);

        ArrayList<Object> genreValues = Collections.list(trainingData.attribute("genre").enumerateValues());

        instance.setValue(trainingData.attribute("userId"), user.getId());
        instance.setValue(trainingData.attribute("genre"), movie.getGenre());
        instance.setValue(trainingData.attribute("durationMinutes"), movie.getDurationMinutes());
        instance.setValue(trainingData.attribute("averageRating"), movie.getAverageRating());

        double[] dist = tree.distributionForInstance(instance);

        int indexSi = trainingData.classAttribute().indexOfValue("si");
        return dist[indexSi]; // probabilidad de que le guste
    }

    private static class MovieScore {
        Movie movie;
        double score;

        MovieScore(Movie movie, double score) {
            this.movie = movie;
            this.score = score;
        }
    }
}
