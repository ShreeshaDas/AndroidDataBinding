package com.android.androiddatabinding.data.fetcher;

import com.android.androiddatabinding.data.response.PeopleApiCall;
import com.android.androiddatabinding.model.GenericResponse;
import com.android.androiddatabinding.model.PeopleList;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.Retrofit;

/**
 * Created by shreesha on 10/7/17.
 */

public class PeopleFetcher {
    public Observable<GenericResponse<ArrayList<PeopleList>>> getPeopleList(Retrofit retrofit, String mediaType, String type, String apiKey, int page) {
        PeopleApiCall nowPlayingMovieApiCall = retrofit.create(PeopleApiCall.class);
        return nowPlayingMovieApiCall.getPeople(mediaType, type, apiKey, page);
    }
}
