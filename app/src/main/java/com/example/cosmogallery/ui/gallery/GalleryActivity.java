package com.example.cosmogallery.ui.gallery;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.cosmogallery.R;
import com.example.cosmogallery.data.model.NasaApod;
import com.example.cosmogallery.databinding.GalleryBinding;
import com.example.cosmogallery.ui.photo.PhotoActivity;
import com.example.cosmogallery.utils.CustomFactory;

import java.io.Serializable;
import java.util.List;

public class GalleryActivity extends AppCompatActivity {

    public static final String PHOTO_URL = "com.example.cosmogallery.ui.NASA_PHOTO_URL";
    public static final String PHOTO_TITLE = "com.example.cosmogallery.ui.PHOTO_TITLE";

    private GalleryAdapter.OnItemClickListener itemClickListener = (url, title) -> {
        Intent intent = new Intent(GalleryActivity.this, PhotoActivity.class);
        intent.putExtra(PHOTO_URL, url);
        intent.putExtra(PHOTO_TITLE, title);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CustomFactory factory = new CustomFactory(itemClickListener);
        GalleryViewModel galleryViewModel = ViewModelProviders.of(this, factory).get(GalleryViewModel.class);
        GalleryBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_gallery);
        binding.setVm(galleryViewModel);
        binding.setLifecycleOwner(this);
    }
}
