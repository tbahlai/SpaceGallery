package com.example.cosmogallery.utils;

import android.app.Application;
import android.os.Build;
import android.transition.TransitionManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.AbsListView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.cosmogallery.data.model.NasaApod;
import com.example.cosmogallery.ui.gallery.GalleryAdapter;
import com.example.cosmogallery.ui.gallery.GalleryViewModel;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.recyclerview.animators.LandingAnimator;
import jp.wasabeef.recyclerview.animators.ScaleInTopAnimator;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class CustomBindingAdapter {

    private static boolean isScrolling = false;
    private static int currentItems, totalItems, scrollItems;

    @BindingAdapter("bind:imageUrl")
    public static void loadImage(ImageView imageView, String urlImage) {
        Picasso.get().load(urlImage).into(imageView);
    }

    @BindingAdapter({"bind:data", "bind:clickHandler"})
    public static void configureRecyclerView(RecyclerView recyclerView,
                                             List<NasaApod> nasaApods,
                                             GalleryAdapter.OnItemClickListener itemClickListener) {

        GalleryAdapter galleryAdapter = new GalleryAdapter(nasaApods, itemClickListener);
        GridLayoutManager manager = new GridLayoutManager(recyclerView.getContext(), 2);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(galleryAdapter);
    }

    @BindingAdapter({"bind:refreshState", "bind:onRefresh"})
    public static void configureSwipeRefreshLayout(SwipeRefreshLayout layout, boolean isLoading,
                                                   SwipeRefreshLayout.OnRefreshListener listener) {
        layout.setOnRefreshListener(listener);
        layout.post(() -> layout.setRefreshing(isLoading));
    }
}
