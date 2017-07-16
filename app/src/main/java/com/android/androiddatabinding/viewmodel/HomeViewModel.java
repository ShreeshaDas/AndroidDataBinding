package com.android.androiddatabinding.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.v4.widget.DrawerLayout;

import com.android.androiddatabinding.model.Movie;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by shreesha on 10/7/17.
 */

public class HomeViewModel extends Observable {

    public ObservableInt peopleRecycler;
    public ObservableInt peopleLabel;
    public ObservableField<DrawerLayout> drawerLayout;

    private Context mContext;
    ArrayList<Movie> mMovies;


    public HomeViewModel(Context context) {
        this.mContext = context;
        this.mMovies = new ArrayList<>();
    }

}
