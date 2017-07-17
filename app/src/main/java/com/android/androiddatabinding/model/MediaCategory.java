package com.android.androiddatabinding.model;

import java.util.ArrayList;

/**
 * Created by shreesha on 10/7/17.
 */

public class MediaCategory {


    private String mCategoryTitle;
    private ArrayList<Movie> mMovies;
    private ArrayList<Tvs> mTv;
    private ArrayList<PeopleList> mPeople;
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

    public ArrayList<Movie> getMovies() {
        return mMovies;
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.mMovies = movies;
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

    public ArrayList<Tvs> getTvShows() {
        return mTv;
    }

    public void setTv(ArrayList<Tvs> tv) {
        this.mTv = tv;
    }

    public ArrayList<PeopleList> getPeople() {
        return mPeople;
    }

    public void setPeople(ArrayList<PeopleList> people) {
        this.mPeople = people;
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
