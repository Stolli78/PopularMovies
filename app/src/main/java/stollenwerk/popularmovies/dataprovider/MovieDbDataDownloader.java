package stollenwerk.popularmovies.dataprovider;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import stollenwerk.popularmovies.MovieData;

/**
 * Created by Markus on 14.02.2016.
 */
public class MovieDbDataDownloader extends AsyncTask<String, Void, ArrayList<MovieData>> {

    private static final String LOG_TAG = MovieDbDataDownloader.class.getSimpleName();
    private MovieDataDownloadObserver observer;

    public MovieDbDataDownloader(MovieDataDownloadObserver observer) {
        this.observer = observer;
    }

    @Override
    protected ArrayList<MovieData> doInBackground(String... params) {
        String movieDbRequest = params[0];
        BufferedReader reader = null;
        String movieData;
        HttpURLConnection urlConnection = null;

        try {
            URL url = new URL(movieDbRequest);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            movieData = buffer.toString();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
        ArrayList<MovieData> movieDataList = null;

        try {
            movieDataList = getMovieDataFromJSON(movieData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movieDataList;
    }

    @Override
    protected void onPostExecute(ArrayList<MovieData> movieData) {
        observer.onMovieDataUpdate(movieData);
    }

    private ArrayList<MovieData> getMovieDataFromJSON(String movieDataStr) throws JSONException {
        final String JSON_RESULTS = "results";
        final String JSON_POSTER_PATH = "poster_path";
        final String JSON_VOTE_AVG = "vote_average";
        final String JSON_OVERVIEW = "overview";
        final String JSON_NAME = "title";
        final String JSON_RELEASE_DATE = "release_date";

        JSONObject movieDataJson = new JSONObject(movieDataStr);
        JSONArray movieArray = movieDataJson.getJSONArray(JSON_RESULTS);
        ArrayList<MovieData> movieDataList = new ArrayList<>();
        for (int i = 0; i < movieArray.length(); i++) {

            JSONObject JSONmovieData = movieArray.getJSONObject(i);
            String posterPath = JSONmovieData.getString(JSON_POSTER_PATH);
            double rating = JSONmovieData.getDouble(JSON_VOTE_AVG);
            String plot = JSONmovieData.getString(JSON_OVERVIEW);
            String name = JSONmovieData.getString(JSON_NAME);
            String releaseDate = JSONmovieData.getString(JSON_RELEASE_DATE);
            MovieData data = new MovieData(name, posterPath, plot, rating, releaseDate);
            movieDataList.add(data);
        }

        return movieDataList;
    }

}
