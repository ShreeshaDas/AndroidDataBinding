package com.android.androiddatabinding.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.android.androiddatabinding.R;
import com.android.androiddatabinding.adapters.MovieCategoryAdapter;
import com.android.androiddatabinding.databinding.ActivityMainBinding;
import com.android.androiddatabinding.model.Movie;
import com.android.androiddatabinding.model.MovieCategory;
import com.android.androiddatabinding.viewmodel.HomeViewModel;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import io.reactivex.disposables.CompositeDisposable;

public class HomeActivity extends AppCompatActivity implements Observer {

    private ActivityMainBinding mActivityMainBinding;
    private HomeViewModel mHomeViewModel;
    private DrawerLayout mDrawer;
    private MovieCategoryAdapter mMovieCategoryAdapter;
    ArrayList<MovieCategory> mMovieCategories = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mHomeViewModel = new HomeViewModel(this);
        mActivityMainBinding.setHomeView(mHomeViewModel);
        setUpToolbar(mActivityMainBinding.toolbar);
        setUpNavigationDrawer();
        setUpRecycleView(mActivityMainBinding.movieList);
        //getTvShows();
        setupObserver(mHomeViewModel);
    }

    private void setUpToolbar(Toolbar toolbar) {
        // Set up the toolbar.
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void setUpNavigationDrawer() {
        // Set up the navigation drawer.
        mDrawer = mActivityMainBinding.drawerLayout;
        mDrawer.setStatusBarBackground(R.color.colorPrimaryDark);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            default:
                                break;
                        }
                        // Close the navigation drawer when an item is selected.
                        menuItem.setChecked(true);
                        mDrawer.closeDrawers();
                        return true;
                    }
                });
    }

    private void setupObserver(Observable observable) {
        observable.addObserver(this);
    }


    private void setUpRecycleView(RecyclerView recycleView) {
        mMovieCategoryAdapter = new MovieCategoryAdapter(this, createMovieCategoryList());
        recycleView.setAdapter(mMovieCategoryAdapter);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Open the navigation drawer when the home icon is selected from the toolbar.
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void update(Observable observable, Object arg) {
    }

    private ArrayList<MovieCategory> createMovieCategoryList() {
        String[] categoryList = getResources().getStringArray(R.array.category);
        for (int i = 0; i < categoryList.length; i++) {
            String[] data = categoryList[i].split(",");
            mMovieCategories.add(new MovieCategory(data[0], MovieCategory.MOVIE_TITLE, data[1], data[2]));
            mMovieCategories.add(new MovieCategory(data[0], MovieCategory.MOVIE_LIST, data[1], data[2]));
        }
        return mMovieCategories;
    }

}
