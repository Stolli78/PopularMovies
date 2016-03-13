package stollenwerk.popularmovies;

import java.io.Serializable;

/**
 * This model class represents a dataset for one movie.
 */
public class MovieData implements Serializable {

    public static final String IMAGE_INTENT_KEY = "MOVIE_IMAGE";
    private String name;
    private String imgSrc;
    private String plotDescription;
    private double rating;
    private String releaseDate;

    public static final String INTENT_KEY = "MOVIE_DATA";

    public MovieData(String name, String imgSrc, String plotDescription, double rating, String releaseDate) {
        this.name = name;
        this.releaseDate = releaseDate;
        this.imgSrc = "http://image.tmdb.org/t/p/w185" + imgSrc;
        this.plotDescription = plotDescription;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public String getPlotDescription() {
        return plotDescription;
    }

    public String getRating() {
        return Double.toString(rating);
    }

    public String getReleaseDate() {
        return releaseDate;
    }
}
