package com.android.androiddatabinding.data.response;

import com.android.androiddatabinding.model.GenericResponse;
import com.android.androiddatabinding.model.People;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by shreesha on 10/7/17.
 */

public interface PeopleApiCall {
    @GET("3/{media}/{type}?language=en-US")
    Observable<GenericResponse<ArrayList<People>>> getPeople(@Path("media") String mediaType, @Path("type") String type, @Query("api_key") String apiKey, @Query("page") int page);
}
