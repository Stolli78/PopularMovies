package stollenwerk.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import stollenwerk.popularmovies.dataprovider.MovieDataDownloadObserver;

/**
 * This is the main activity of the app, showing an movie overview. The options menu allows the user to switch between
 * different view-modes.
 */
public class MovieOverviewActivity extends AppCompatActivity {

    private static final String LOG_TAG = MovieOverviewActivity.class.getSimpleName();
    private MovieOverviewPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_overview);
        final GridView moviePosterView = (GridView)findViewById(R.id.moviePosterGridView);
        moviePosterView.setOnItemClickListener(getOnItemClickListener(moviePosterView));
        MoviePosterAdapter moviePosterAdapter = new MoviePosterAdapter(this, new ArrayList<MovieData>());
        moviePosterView.setAdapter(moviePosterAdapter);

        this.presenter = new MovieOverviewPresenter(this);
        presenter.loadMoviesFromMovieDb();
    }

    @NonNull
    private AdapterView.OnItemClickListener getOnItemClickListener(final GridView moviePosterView) {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MoviePosterAdapter adapter = (MoviePosterAdapter)moviePosterView.getAdapter();
                Intent detailIntent = new Intent(view.getContext(), MovieDetailsActivity.class);
                detailIntent.putExtra(MovieData.INTENT_KEY, adapter.getItem(position));
                startActivity(detailIntent);
            }
        };
    }

    void updateMovieList(List<MovieData> data) {
        GridView gridView = (GridView) findViewById(R.id.moviePosterGridView);
        MoviePosterAdapter adapter = (MoviePosterAdapter) gridView.getAdapter();
        adapter.clear();
        adapter.addAll(data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.overview_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MovieDbDataProvider.VIEW_MODE newViewMode = MovieDbDataProvider.VIEW_MODE.POPULAR;
        switch(item.getItemId()) {
            case R.id.option_popular_movies:
                newViewMode = MovieDbDataProvider.VIEW_MODE.POPULAR;
                break;
            case R.id.option_top_rated_movies:
                newViewMode = MovieDbDataProvider.VIEW_MODE.TOP_RATED;
                break;
            case R.id.option_refresh_overview:
                Toast.makeText(this, "refreshing movie list", Toast.LENGTH_SHORT).show();
                break;
            default:
                Log.e(LOG_TAG, "Unknown option item ID: " + item.getItemId());
                newViewMode = MovieDbDataProvider.VIEW_MODE.POPULAR;
        }
        presenter.changeViewMode(newViewMode);

        return true;
    }

    void setViewModeLabel(MovieDbDataProvider.VIEW_MODE newViewMode) {
        TextView viewModeLabel = (TextView) findViewById(R.id.view_mode_label);
        if(newViewMode == MovieDbDataProvider.VIEW_MODE.POPULAR) {
            viewModeLabel.setText(R.string.option_popular_movies);
        }
        else {
            viewModeLabel.setText(R.string.option_top_rated_movies);
        }
    }
}
