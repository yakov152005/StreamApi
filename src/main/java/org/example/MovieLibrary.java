package org.example;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


public class MovieLibrary {
    private List<Director> directorList;
    private List<Actor> actorList;
    private List<Movie> movieList;

    public MovieLibrary () {
        List<List<String>> directorsData = Utils.readFile("directors.csv");
        this.directorList = createDirector(directorsData);

        List<List<String>> actorsData = Utils.readFile("actors.csv");
        this.actorList = createActor(actorsData);

        List<List<String>> moviesData = Utils.readFile("movies.csv");
        this.movieList = createMovie(moviesData,directorList,actorList);



    }


    public static List<Director> createDirector(List<List<String>> directorsData){
        List<Director> directorList = directorsData.stream()
                .map(parts -> {
                    try {
                        int id = Integer.parseInt(parts.get(0).trim());
                        String name = parts.get(1).trim();
                        int year = Integer.parseInt(parts.get(2).trim());
                        return new Director(id, name, year);
                    } catch (NumberFormatException e) {
                        return null;
                    }
                }).toList();
        return directorList;
    }
    public static List<Actor> createActor(List<List<String>> actorsData){
        List<Actor> actorList = actorsData.stream()
                .map(parts-> {
                    try {
                        int id = Integer.parseInt(parts.get(0).trim());
                        String name = parts.get(1).trim();
                        int birthdayYear = Integer.parseInt(parts.get(2).trim());
                        return new Actor(id,name,birthdayYear);
                    } catch (NumberFormatException e) {
                        return null;
                    }
                }).toList();
        return actorList;
    }
    public static List<Movie> createMovie(List<List<String>> moviesData, List<Director> directorList,List<Actor> actorList){
        List<Movie> moviesList = moviesData.stream()
                .map(part-> {
                    int id = Integer.parseInt(part.get(0).trim());
                    String name = part.get(1).trim();
                    int directorID = Integer.parseInt(part.get(2).trim());
                    Director director = null;
                    for (Director d : directorList) {
                        if (d.getId() == directorID) {
                            director = d;
                            break;
                        }
                    }
                    List<Actor> actors = new ArrayList<>();
                    for (int i = 3; i <= 4; i++) {
                        int idActor = Integer.parseInt(part.get(i).trim());
                        for (Actor a : actorList) {
                            if (a.getId() == idActor){
                                actors.add(a);
                                break;
                            }
                        }
                    }
                    int releaseYear = Integer.parseInt(part.get(5).trim());
                    String genre = part.get(6).trim();
                    return new Movie(id,name,director,actors,releaseYear,genre);
                }).toList();
        return moviesList;
    }

       // 1. מצא את כל movies.csv ששוחררו בשנה מסוימת.
      // // החזר רשימה של כותרות סרטים.
       public List<String> findMoviesByYear(int year) {
           return movieList.stream()
                   .filter(movie -> movie.getReleaseYear() == year)
                   .map(Movie::getTitle)
                   .toList();
       }

    // // 2. מצא את כל movies.csv של ז'אנר ספציפי.
    // // החזר רשימה של כותרות סרטים.
    public List<String> findMoviesByGenre(String genre) {
        return movieList.stream()
                .filter(movie -> movie.getGenre().equalsIgnoreCase(genre))
                .map(Movie::getTitle)
                .toList();
    }

    // // 3. מצא את כל directors.csv שבימו לפחות N movies.csv.
    // // החזר רשימה של שמות מנהלים.
    public List<String> findDirectorsWithAtLeastNMovies(int n) {
        return directorList.stream()
                .filter(director -> movieList.stream()
                        .filter(movie -> movie.getDirector().getId() == director.getId()).count() >= n )
                .map(Director::getName)
                .toList();
    }

    // 4. מצא את כל actors.csv שהופיעו ב-movies.csv מז'אנר ספציפי.
    // החזר רשימה של שמות שחקנים.
    public List<String> findActorsInGenre(String genre) {
        return movieList.stream()
                .filter(movie -> movie.getGenre().equalsIgnoreCase(genre))
                .flatMap(movie -> movie.getActors().stream())
                .distinct()
                .map(Actor::getName)
                .toList();
    }

    // 5. מצא את שנת ההשקה הממוצעת של movies.csv עבור במאי ספציפי.
    // החזר ערך כפול.
    public double findAverageReleaseYearForDirector(String directorName) {
        int counter = (int) movieList.stream()
                .filter(movie -> movie.getDirector().getName().equalsIgnoreCase(directorName))
                .count();
        long sumYears = movieList.stream()
                .filter(movie -> movie.getDirector().getName().equalsIgnoreCase(directorName))
                .mapToLong(Movie::getReleaseYear)
                .sum();
        return (double) sumYears / counter;
    }


    // 6. מצא את ה-N השחקנים המובילים.csv שהופיעו ברוב ה-movies.csv.
    // החזר רשימה של שמות שחקנים.
    public List<String> findTopNActors(int n) {
        List<String> allActors = movieList.stream()
                .flatMap(movie -> movie.getActors().stream())
                .map(Actor::getName).toList();


        Map<String, Long> actorCount = allActors.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

//        for (Map.Entry<String, Long> s  : actorCount.entrySet()){
//            System.out.println(s);
//        }
//        System.out.println();

        List<String> topActors = actorCount.entrySet().stream()
                .filter(entry -> entry.getValue() >= n )
                .map(Map.Entry::getKey)
                .toList();

        return topActors;
    }


    // 7. מצא את כל movies.csv שבהם שחקן ובמאי ספציפיים עבדו יחד.
    // // החזר רשימה של כותרות סרטים.
    public List<String> findMoviesByActorAndDirector(String actorName, String directorName) {
        return movieList.stream()
                .filter(movie -> movie.getDirector().getName().equalsIgnoreCase(directorName)
                        && movie.getActors().stream()
                        .anyMatch(actor -> actor.getName().equalsIgnoreCase(actorName)))
                .map(Movie::getTitle)
                .toList();
    }

    // 8. מצא את הז'אנר הנפוץ ביותר עבור כל שחקן.
    // החזר מפה עם שמות שחקנים כמפתחות והז'אנר הנפוץ ביותר כערכים.
    public Map<String, String> findMostCommonGenrePerActor() {
        Map<String, Map<String, Long>> actorGenreCount = new HashMap<>();

        movieList.forEach(movie -> {
            String genre = movie.getGenre();
            movie.getActors().forEach(actor -> {
                String actorName = actor.getName();
                actorGenreCount.putIfAbsent(actorName, new HashMap<>());
                Map<String, Long> genreCount = actorGenreCount.get(actorName);
                genreCount.put(genre, genreCount.getOrDefault(genre, 0L) + 1);
            });
        });


        Map<String, String> mostCommonGenrePerActor = new HashMap<>();
        actorGenreCount.forEach((actorName, genreCount) -> {

            String mostCommonGenre = genreCount.entrySet().stream()
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey)
                    .orElse(null);

            mostCommonGenrePerActor.put(actorName, mostCommonGenre);
        });

        return mostCommonGenrePerActor;
    }

    // 9. מצא את הבמאי עם דירוג הסרט הממוצע הגבוה ביותר (1-5).
    // // נניח שקיימת שיטה: double getMovieRating(Movie movie), שמחזירה דירוג של סרט.
  // Return the director name.

    public String findDirectorWithHighestAverageRating() {
        return null;
    }


}
