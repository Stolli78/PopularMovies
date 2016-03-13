package stollenwerk.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Markus on 12.02.2016.
 */
public class MoviePosterAdapter extends ArrayAdapter<MovieData> {

    public MoviePosterAdapter(Context context, ArrayList<MovieData> objects) {
        super(context, -1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.movie_poster_row, parent, false);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.moviePosterImageView);

        String url = getItem(position).getImgSrc();
        Picasso.with(getContext()).load(url).into(imageView);

        return rowView;
    }
}
