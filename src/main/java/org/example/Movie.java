package org.example;

import java.util.List;

public class Movie {
    private int id; // מספר סידורי סרט
    private String title; // כותרת
    private Director director;// מזהה בימאי
    private List<Actor> actors;// מזהה שחקן + מזהה שחקן
    private int releaseYear;// שנת יציאה
    private String genre;// זנאר

    public Movie(int id, String title, Director director, List<Actor> actors, int releaseYear, String genre) {
        this.id = id;
        this.title = title;
        this.director = director;
        this.actors = actors;
        this.releaseYear = releaseYear;
        this.genre = genre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(this.id).append("-").append(this.title).append(" ").append(this.releaseYear).append(" ").append(this.genre).append("\n");
        sb.append("Director -- > ").append(this.director.toString()).append("\n");
        sb.append("Actors -- > ").append(this.actors.toString());
        return sb.toString();
    }
}
