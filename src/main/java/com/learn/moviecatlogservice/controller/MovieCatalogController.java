package com.learn.moviecatlogservice.controller;

import com.learn.moviecatlogservice.model.CatalogItem;
import com.learn.moviecatlogservice.model.Movie;
import com.learn.moviecatlogservice.model.Rating;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("catalog")
public class MovieCatalogController {

    @GetMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId")  String userId) {
        RestTemplate restTemplate = new RestTemplate();
        List<Rating> ratings = Arrays.asList(new Rating("1234",5),
                new Rating("5678",2));


        return ratings
                .stream()
                .map(rating -> {
                    Movie movie = restTemplate.getForObject("http://localhost:8082/movies/"+rating.getMovieId(), Movie.class);
                 return    new CatalogItem(movie.getName(), "It is a kannada Movie", rating.getRating());
                })
                .collect(Collectors.toList());




        //for each movieId get Movie details
//            return Collections.singletonList(
//                    new CatalogItem("Raja Huli","It is a kannada Movie",5)
//            );
    }
}
