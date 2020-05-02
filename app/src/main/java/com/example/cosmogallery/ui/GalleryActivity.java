package com.example.cosmogallery.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.cosmogallery.BuildConfig;
import com.example.cosmogallery.R;
import com.example.cosmogallery.data.ApiUtils;
import com.example.cosmogallery.databinding.GalleryBinding;
import com.google.android.material.snackbar.Snackbar;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class GalleryActivity extends AppCompatActivity {

    private static final int SPAN_COUNT = 2;
    public static final String PHOTO_URL = "com.example.cosmogallery.ui.NASA_PHOTO_URL";
    public static final String PHOTO_TITLE = "com.example.cosmogallery.ui.PHOTO_TITLE";

//    RecyclerView recyclerView;
//    GalleryAdapter galleryAdapter;
//    SwipeRefreshLayout refreshLayout;

//    CompositeDisposable disposable = new CompositeDisposable();

    private GalleryViewModel galleryViewModel;


//    private SwipeRefreshLayout.OnRefreshListener listener = this::getPhotos;

    private GalleryAdapter.OnItemClickListener itemClickListener = new GalleryAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(String url, String title) {
            Intent intent = new Intent(GalleryActivity.this, PhotoActivity.class);
            intent.putExtra(PHOTO_URL, url);
            intent.putExtra(PHOTO_TITLE, title);
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_gallery);
//
        galleryViewModel = new GalleryViewModel(itemClickListener);
        GalleryBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_gallery);
        binding.setVm(galleryViewModel);


        galleryViewModel.loadPhotos();


//        recyclerView = findViewById(R.id.recycler_view);
//        refreshLayout = findViewById(R.id.refresher);

//        refreshLayout.setOnRefreshListener(listener);
//
//        recyclerView.setLayoutManager(new GridLayoutManager(this, SPAN_COUNT));
//        galleryAdapter = new GalleryAdapter(itemClickListener);
//        recyclerView.setAdapter(galleryAdapter);
//        getPhotos();
    }

//    private void getPhotos() {
//        disposable.add(ApiUtils.getNasaApi().getApodCountPhotos(BuildConfig.API_KEY, COUNT_IMAGE, true)
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnSubscribe(disposable1 -> refreshLayout.setRefreshing(true))
//                .subscribeOn(Schedulers.io())
//                .doFinally(() -> refreshLayout.setRefreshing(false))
//                .subscribe(nasaApods -> galleryAdapter.setPhotos(nasaApods),
//                        throwable ->
//                                Snackbar.make(recyclerView, R.string.error_loading, Snackbar.LENGTH_SHORT).show())
//        );
//    }

    @Override
    protected void onDestroy() {
        galleryViewModel.dispatchDestroy();
        super.onDestroy();
    }
}
