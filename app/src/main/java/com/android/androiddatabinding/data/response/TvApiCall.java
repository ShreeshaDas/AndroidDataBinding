package com.android.androiddatabinding.data.response;

import com.android.androiddatabinding.model.MoviesResponse;
import com.android.androiddatabinding.model.Tv;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by shreesha on 10/7/17.
 */

public interface TvApiCall {
    @GET("3/{media}/{type}?language=en-US")
    Observable<Tv> getTvs(@Path("media") String mediaType, @Path("type") String type, @Query("api_key") String apiKey, @Query("page") int page);
}
