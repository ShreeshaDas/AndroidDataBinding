package com.android.androiddatabinding.adapters;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.androiddatabinding.R;
import com.android.androiddatabinding.databinding.MovieItemBinding;
import com.android.androiddatabinding.model.Movie;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by shreesha on 14/2/17.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private ArrayList<Movie> mMovies;
    private Context mContext;

    public MoviesAdapter(Context context, ArrayList<Movie> movies) {
        this.mMovies = movies;
        this.mContext = context;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MovieItemBinding movieItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.movie_item,
                parent,
                false);
        return new MovieViewHolder(movieItemBinding);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.bindMovie(mMovies.get(position));
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {

        MovieItemBinding movieItemBinding;

        public MovieViewHolder(MovieItemBinding movieItemBinding) {
            super(movieItemBinding.getRoot());
            this.movieItemBinding = movieItemBinding;
        }

        void bindMovie(Movie movie) {
            movieItemBinding.setMovie(movie);
            movieItemBinding.executePendingBindings();
        }
    }

    @BindingAdapter({"android:src"})
    public static void setImageViewResource(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load("https://image.tmdb.org/t/p/w500" + url).centerCrop().into(imageView);
    }

    public void setData(ArrayList<Movie> movies) {
        this.mMovies.clear();
        this.mMovies.addAll(movies);
        notifyDataSetChanged();
    }
}
