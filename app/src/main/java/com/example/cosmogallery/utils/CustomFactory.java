package com.example.cosmogallery.utils;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.cosmogallery.ui.gallery.GalleryAdapter;
import com.example.cosmogallery.ui.gallery.GalleryViewModel;

public class CustomFactory extends ViewModelProvider.NewInstanceFactory {

    private GalleryAdapter.OnItemClickListener onItemClickListener;

    public CustomFactory(GalleryAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new GalleryViewModel(onItemClickListener);
    }
}
