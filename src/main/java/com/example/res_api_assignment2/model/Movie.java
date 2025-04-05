package com.example.res_api_assignment2.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "movies")
public class Movie {
    @Id
    private String id;

    private String name;
    private double price;
    private String synopsis;
    private double rentPrice;
    private double outrightPrice;
    private String smallPosterPath;
    private String largePosterPath;
    @Field("isMovie")
    private boolean isMovie;
    @Field("isFeatured")
    private boolean isFeatured;

    // Constructors
    public Movie() {

    }
    public Movie(String name, double price, String synopsis,  double rentPrice, double outrightPrice, String smallPosterPath, String largePosterPath,  boolean isMovie, boolean isFeatured) {
        this.name = name;
        this.price = price;
        this.synopsis = synopsis;
        this.rentPrice = rentPrice;
        this.outrightPrice = outrightPrice;
        this.smallPosterPath = smallPosterPath;
        this.largePosterPath = largePosterPath;
        this.isMovie = isMovie;
        this.isFeatured = isFeatured;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getRentPrice() {
        return rentPrice;
    }

    public void setRentPrice(double rentPrice) {
        this.rentPrice = rentPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public double getOutrightPrice() {
        return outrightPrice;
    }

    public void setOutrightPrice(double outrightPrice) {
        this.outrightPrice = outrightPrice;
    }

    public boolean isMovie() {
        return isMovie;
    }

    public void setMovie(boolean isMovie) {
        this.isMovie = isMovie;
    }

    public boolean isFeatured() {
        return isFeatured;
    }

    public void setFeatured(boolean isFeatured) {
        this.isFeatured = isFeatured;
    }

    public String getSmallPosterPath() {
        return smallPosterPath;
    }

    public void setSmallPosterPath(String smallPosterPath) {
        this.smallPosterPath = smallPosterPath;
    }

    public String getLargePosterPath() {
        return largePosterPath;
    }

    public void setLargePosterPath(String largePosterPath) {
        this.largePosterPath = largePosterPath;
    }



}

