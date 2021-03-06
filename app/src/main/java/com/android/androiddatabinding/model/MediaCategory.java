package com.android.androiddatabinding.model;

import java.util.ArrayList;

/**
 * Created by shreesha on 10/7/17.
 */

public class MediaCategory {


    private String mCategoryTitle;
    private GenericResponse<ArrayList<Movie>> mMovies;
    private GenericResponse<ArrayList<Tvs>> mTv;
    private GenericResponse<ArrayList<People>> mPeople;
    private String mQueryType;
    private String mMediaType;
    private NetworkError mNetworkError;
    private int mViewType;

    public MediaCategory(String movieTitle, int viewType, String queryType, String mediaType) {
        this.mCategoryTitle = movieTitle;
        this.mViewType = viewType;
        this.mQueryType = queryType;
        this.mMediaType = mediaType;
    }

    public String getMediaCategory() {
        return mCategoryTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.mCategoryTitle = movieTitle;
    }

    public GenericResponse<ArrayList<Movie>> getMovies() {
        return mMovies;
    }

    public void setMovies(GenericResponse<ArrayList<Movie>> movieGenericResponse) {
        if (this.mMovies == null) {
            this.mMovies = movieGenericResponse;
        } else {
            this.mMovies.getResults().addAll(movieGenericResponse.getResults());
        }
    }

    public String getQueryType() {
        return mQueryType;
    }

    public void setQueryType(String mQueryType) {
        this.mQueryType = mQueryType;
    }

    public String getMediaType() {
        return mMediaType;
    }

    public void setMediaType(String mMediaType) {
        this.mMediaType = mMediaType;
    }

    public NetworkError getNetworkError() {
        return mNetworkError;
    }

    public void setNetworkError(NetworkError networkError) {
        this.mNetworkError = networkError;
    }

    public int getGetViewType() {
        return mViewType;
    }

    public void setViewType(int viewType) {
        this.mViewType = viewType;
    }

    public GenericResponse<ArrayList<Tvs>> getTvShows() {
        return mTv;
    }

    public void setTv(GenericResponse<ArrayList<Tvs>> tvsGenericResponse) {
        if (this.mTv == null) {
            this.mTv = tvsGenericResponse;
        } else {
            this.mTv.getResults().addAll(tvsGenericResponse.getResults());
        }
    }

    public GenericResponse<ArrayList<People>> getPeople() {
        return mPeople;
    }

    public void setPeople(GenericResponse<ArrayList<People>> peopleGenericResponse) {
        if (this.mPeople == null) {
            this.mPeople = peopleGenericResponse;
        } else {
            this.mPeople.getResults().addAll(peopleGenericResponse.getResults());
        }

    }


    @Override
    public int hashCode() {
        int prime = 37;
        int result = 1;
        result = prime * result + mCategoryTitle.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (getClass() != o.getClass()) return false;
        MediaCategory media = (MediaCategory) o;
        if (!this.mCategoryTitle.equals(media.getMediaCategory())) return false;
        return true;
    }
}
