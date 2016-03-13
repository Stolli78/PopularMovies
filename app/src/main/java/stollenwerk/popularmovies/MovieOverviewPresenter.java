package stollenwerk.popularmovies;

import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

import stollenwerk.popularmovies.dataprovider.MovieDataDownloadObserver;

/**
 * The presenter class controls the MovieOverviewActivity and handles data requests etc..
 */
public class MovieOverviewPresenter implements MovieDataDownloadObserver {

    private final MovieOverviewActivity view;
    private MovieDbDataProvider.VIEW_MODE viewMode = MovieDbDataProvider.VIEW_MODE.POPULAR;

    public MovieOverviewPresenter(MovieOverviewActivity view) {
        this.view = view;
    }

    public void changeViewMode(MovieDbDataProvider.VIEW_MODE viewMode) {
        this.viewMode = viewMode;
        loadMoviesFromMovieDb();
    }

    @Override
    public void onMovieDataUpdate(ArrayList<MovieData> data) {
        view.setViewModeLabel(viewMode);
        if(data != null) {
            view.updateMovieList(data);
        }
        else {
            Toast.makeText(view, "No movie data found, please try again later.", Toast.LENGTH_LONG).show();
        }
    }

    public void loadMoviesFromMovieDb() {
        MovieDbDataProvider.getMostPopularMovies(viewMode, this);
    }
}
