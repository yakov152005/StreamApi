package org.example;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        MovieLibrary movieLibrary = new MovieLibrary();


        System.out.println("Enter the desired year and it will find you the movies from hundreds of years -->");
        int year = s.nextInt();
        List<String> titleForThisYear = movieLibrary.findMoviesByYear(year);
        printList(titleForThisYear);
        s.nextLine();//clear the buffer

        System.out.println("Enter the genre you are looking for --> ");
        String genre = s.nextLine();
        List<String> genreList = movieLibrary.findMoviesByGenre(genre);
        printList(genreList);

        System.out.println("Enter any number 'n' to find directors who directed at least 'n' movies like you entered -->");
        int n = s.nextInt();
        List<String> directorNList = movieLibrary.findDirectorsWithAtLeastNMovies(n);
        printList(directorNList);

        System.out.println("Find actors by genre -- >");
        String  genreActor = s.nextLine();
        List<String> actorList = movieLibrary.findActorsInGenre(genreActor);
        printList(actorList);
        double d = movieLibrary.findAverageReleaseYearForDirector("Ang Lee");
        System.out.println(d);

        List<String> topNActors = movieLibrary.findTopNActors(6);
        printList(topNActors);

        List<String> s = movieLibrary.findMoviesByActorAndDirector("Julia Roberts",
                "Kathryn Bigelow");
        printList(s);
        Map<String,String> mapActor = movieLibrary.findMostCommonGenrePerActor();
        for (Map.Entry<String,String> stringStringEntry : mapActor.entrySet()){
            System.out.println(stringStringEntry);
        }

    }

    public static void printList(List<String> list){
        for (String s : list)
            System.out.println(s);
        System.out.println();
    }
    public static Scanner s = new Scanner(System.in);
}
