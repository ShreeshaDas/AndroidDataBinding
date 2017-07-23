package com.android.androiddatabinding.binding;

import android.databinding.BindingAdapter;
import android.text.TextUtils;
import android.widget.ImageView;

import com.android.androiddatabinding.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * Created by shreesha on 17/7/17.
 */

public class GlideImageBindingAdapter {

    @BindingAdapter({"android:src"})
    public static void setPeopleImageViewResource(ImageView imageView, String url) {
        switch (imageView.getId()) {
            case R.id.movie_poster:
                if (!TextUtils.isEmpty(url)) {
                    Glide.with(imageView.getContext())
                            .load("https://image.tmdb.org/t/p/w500" + url)
                            .apply(RequestOptions.centerCropTransform())
                            .transition(withCrossFade())
                            .into(imageView);
                } else {
                    Glide.with(imageView.getContext())
                            .load(R.drawable.movie_placeholder)
                            .apply(RequestOptions.centerCropTransform())
                            .transition(withCrossFade())
                            .into(imageView);
                }
                break;
            case R.id.tv_poster:
                Glide.with(imageView.getContext())
                        .load("https://image.tmdb.org/t/p/w500" + url)
                        .apply(RequestOptions.centerCropTransform())
                        .transition(withCrossFade())
                        .into(imageView);
                break;
            case R.id.people_profile:
                if (TextUtils.isEmpty(url)) {
                    Glide.with(imageView.getContext())
                            .load(R.drawable.people_placeholder)
                            .apply(RequestOptions.circleCropTransform())
                            .transition(withCrossFade())
                            .into(imageView);
                } else {
                    Glide.with(imageView.getContext())
                            .load("https://image.tmdb.org/t/p/w500" + url)
                            .apply(RequestOptions.circleCropTransform())
                            .transition(withCrossFade())
                            .into(imageView);
                }

                break;
        }
    }
}
