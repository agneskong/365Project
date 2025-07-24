package PopcornPicks.model;

public class Movie {
    private String title;
    private String genre;
    private int year;
    private float rating;
    private String posterPath;
    private String synopsis;

    public Movie(String title, String genre, int year, float rating, String posterPath, String synopsis) {
        this.title = title;
        this.genre = genre;
        this.year = year;
        this.rating = rating;
        this.posterPath = posterPath;
        this.synopsis = synopsis;
    }

    public String getTitle() { return title; }

    public String getGenre() { return genre; }

    public int getYear() { return year; }

    public float getRating() { return rating; }

    public String getPosterPath() { return posterPath; }

    public String getSynopsis() { return synopsis; }
}
