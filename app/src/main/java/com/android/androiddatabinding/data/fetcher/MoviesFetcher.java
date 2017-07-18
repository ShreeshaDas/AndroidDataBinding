package com.android.androiddatabinding.data.fetcher;

import com.android.androiddatabinding.data.response.MovieApiCall;
import com.android.androiddatabinding.model.GenericResponse;
import com.android.androiddatabinding.model.Movie;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.Retrofit;

/**
 * Created by shreesha on 10/7/17.
 */

public class MoviesFetcher {
    public Observable<GenericResponse<ArrayList<Movie>>> getMoviesList(Retrofit retrofit, String mediaType, String type, String apiKey, int page) {
        MovieApiCall nowPlayingMovieApiCall = retrofit.create(MovieApiCall.class);
        return nowPlayingMovieApiCall.getMovies(mediaType, type, apiKey, page);
    }
}
