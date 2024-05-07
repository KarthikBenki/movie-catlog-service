package com.learn.moviecatlogservice.controller;

import com.learn.moviecatlogservice.model.CatalogItem;
import com.learn.moviecatlogservice.model.Movie;
import com.learn.moviecatlogservice.model.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog") // This maps incoming requests to the "/catalog" path
public class MovieCatalogController {

    @Autowired
    private RestTemplate restTemplate;  // Injected RestTemplate for making REST calls

    @Autowired
    WebClient.Builder webClientBuilder; // Injected WebClient builder for reactive calls

    @Value("${movie.info.uri}")  // Injected value from properties file for movie info URI
    private String movie_info_uri;

    @GetMapping("/{userId}")  // Maps GET requests to "/catalog/{userId}"
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {

        // Hardcoded list of ratings for demonstration purposes (replace with actual logic)
        List<Rating> ratings = Arrays.asList(new Rating("1234", 5), new Rating("5678", 2));

        // Use WebClient for reactive calls to retrieve movie details
        return ratings.stream()
                .map(rating -> {

                    Movie movie = restTemplate.getForObject(movie_info_uri + rating.getMovieId(), Movie.class);
                    /*
                    Movie movie = webClientBuilder.build()
                            .get()  // Specifies a GET request
                            .uri(movie_info_uri + rating.getMovieId())  // Constructs the URI with movie ID
                            .retrieve()
                            .bodyToMono(Movie.class)  // Convert response body to a Mono containing Movie object
                            .block();  // Blocks until the Mono emits a value (might not be suitable for production)

                     */

                    // Create a CatalogItem with retrieved movie information and rating
                    return new CatalogItem(movie.getName(), "It is a kannada Movie", rating.getRating());
                })
                .collect(Collectors.toList());
    }
}
