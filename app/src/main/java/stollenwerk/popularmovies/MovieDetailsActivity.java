package stollenwerk.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * This activity shows detailed information about the selected movie.
 */
public class MovieDetailsActivity extends AppCompatActivity {

    private static final String LOG_TAG = MovieDetailsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Intent detailIntent = getIntent();
        MovieData movieData = (MovieData) detailIntent.getSerializableExtra(MovieData.INTENT_KEY);
        updateView(movieData);
    }

    private void updateView(MovieData movieData) {
        TextView title = (TextView) findViewById(R.id.movieTitleText);
        title.setText(movieData.getName());
        TextView plot = (TextView) findViewById(R.id.movieDescriptionText);
        plot.setText(movieData.getPlotDescription());
        plot.setMovementMethod(new ScrollingMovementMethod());
        TextView rating = (TextView) findViewById(R.id.ratingText);
        rating.setText(movieData.getRating());
        TextView releaseDate = (TextView) findViewById(R.id.releaseDateText);
        releaseDate.setText(movieData.getReleaseDate());
        ImageView movieThumbnail = (ImageView)findViewById(R.id.movieThumbnailView);
        Picasso.with(this).load(movieData.getImgSrc()).into(movieThumbnail);
    }
}
