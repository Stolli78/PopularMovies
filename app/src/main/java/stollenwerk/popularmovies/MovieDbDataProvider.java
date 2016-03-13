package stollenwerk.popularmovies;

import android.net.Uri;

import stollenwerk.popularmovies.dataprovider.MovieDataDownloadObserver;
import stollenwerk.popularmovies.dataprovider.MovieDbDataDownloader;

/**
 * This class assembles the movieDB API URL for movie-list data requests. Please note, that you need to use your own API_KEY here.
 */
public class MovieDbDataProvider {

    private static final String API_KEY = "YOUR_API_KEY";
    private static final String MOVIE_DB_URL ="api.themoviedb.org";
    private static final String MOVIE_DB_POPULAR_URL = "/3/movie/popular";
    private static final String MOVIE_DB_TOP_RATED_URL = "/3/movie/top_rated";
    public enum VIEW_MODE {
        POPULAR,
        TOP_RATED
    }

    public static void getMostPopularMovies(VIEW_MODE viewMode, MovieDataDownloadObserver observer) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http");
        builder.authority(MOVIE_DB_URL);
        if(viewMode.equals(VIEW_MODE.POPULAR)) {
            builder.path(MOVIE_DB_POPULAR_URL);
        }
        else {
            builder.path(MOVIE_DB_TOP_RATED_URL);
        }
        builder.appendQueryParameter("api_key", API_KEY);
        Uri request = builder.build();
        new MovieDbDataDownloader(observer).execute(request.toString());
    }



}
