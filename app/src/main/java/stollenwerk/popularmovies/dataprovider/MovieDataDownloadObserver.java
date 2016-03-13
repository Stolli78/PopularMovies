package stollenwerk.popularmovies.dataprovider;

import java.util.ArrayList;

import stollenwerk.popularmovies.MovieData;

/**
 * Created by Markus on 14.02.2016.
 */
public interface MovieDataDownloadObserver {

    void onMovieDataUpdate(ArrayList<MovieData> data);
}
