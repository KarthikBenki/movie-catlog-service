package com.learn.movieratingservice.model;

import com.learn.moviecatlogservice.model.Rating;

import java.util.List;

public class UserRating {
    private List<Rating> ratings;

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }
}
