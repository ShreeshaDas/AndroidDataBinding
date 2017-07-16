package com.android.androiddatabinding.data.fetcher;

import com.android.androiddatabinding.data.response.MovieApiCall;
import com.android.androiddatabinding.data.response.TvApiCall;
import com.android.androiddatabinding.model.MoviesResponse;
import com.android.androiddatabinding.model.Tv;

import io.reactivex.Observable;
import retrofit2.Retrofit;

/**
 * Created by shreesha on 10/7/17.
 */

public class TvFetcher {
    public Observable<Tv> getTvsList(Retrofit retrofit, String mediaType, String type, String apiKey, int page) {
        TvApiCall tvApiCall = retrofit.create(TvApiCall.class);
        return tvApiCall.getTvs(mediaType, type, apiKey, page);
    }
}
