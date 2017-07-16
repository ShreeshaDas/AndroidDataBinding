package com.android.androiddatabinding;

import android.app.Application;
import android.content.Context;

import com.android.androiddatabinding.data.factory.ApiFactory;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Created by Shreesha on 16-07-2017.
 */

public class AndroidDataBindingApplication extends Application {

    private Scheduler mScheduler;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static AndroidDataBindingApplication get(Context context) {
        return (AndroidDataBindingApplication) context.getApplicationContext();
    }

    public static AndroidDataBindingApplication getApplication(Context context) {
        return AndroidDataBindingApplication.get(context);
    }

    public Retrofit getRetrofit() {
        return ApiFactory.getClient();
    }

    public Scheduler subscribeScheduler() {
        if (mScheduler == null) {
            mScheduler = Schedulers.io();
        }
        return mScheduler;
    }
}
