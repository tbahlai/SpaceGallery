package com.example.cosmogallery.utils;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.cosmogallery.data.model.NasaApod;
import com.example.cosmogallery.ui.gallery.GalleryAdapter;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomBindingAdapter {

    @BindingAdapter("bind:imageUrl")
    public static void loadImage(ImageView imageView, String urlImage) {
        Picasso.get().load(urlImage).into(imageView);
    }

    @BindingAdapter({"bind:data", "bind:clickHandler"})
    public static void configureRecyclerView(RecyclerView recyclerView,
                                             List<NasaApod> nasaApods,
                                             GalleryAdapter.OnItemClickListener itemClickListener) {
//        GalleryAdapter galleryAdapter = new GalleryAdapter(nasaApods, itemClickListener);
        GalleryAdapter galleryAdapter = new GalleryAdapter(nasaApods, itemClickListener);
        recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), 2));
       // recyclerView.setItemAnimator(new SlideInUpAnimator(new OvershootInterpolator(1f)));
        recyclerView.setAdapter(galleryAdapter);
    }

    @BindingAdapter({"bind:refreshState", "bind:onRefresh"})
    public static void configureSwipeRefreshLayout(SwipeRefreshLayout layout, boolean isLoading,
                                                   SwipeRefreshLayout.OnRefreshListener listener) {
        layout.setOnRefreshListener(listener);
        layout.post(() -> layout.setRefreshing(isLoading));
    }
}
