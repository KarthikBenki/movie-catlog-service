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
@RequestMapping("catalog")
public class MovieCatalogController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    WebClient.Builder webClientBuilder;

    @Value("${movie.info.uri}")
    private String movie_info_uri;

    @GetMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId")  String userId) {
        List<Rating> ratings = Arrays.asList(new Rating("1234",5),
                new Rating("5678",2));


        return ratings
                .stream()
                .map(rating -> {
//                    Movie movie = restTemplate.getForObject(movie_info_uri+rating.getMovieId(), Movie.class);

                    Movie movie = webClientBuilder.build()
                            .get()
                            .uri(movie_info_uri + rating.getMovieId())
                            .retrieve()
                            .bodyToMono(Movie.class)
                            .block();

                    return    new CatalogItem(movie.getName(), "It is a kannada Movie", rating.getRating());
                })
                .collect(Collectors.toList());




        //for each movieId get Movie details
//            return Collections.singletonList(
//                    new CatalogItem("Raja Huli","It is a kannada Movie",5)
//            );
    }
}
