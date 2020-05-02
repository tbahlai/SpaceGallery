package com.example.cosmogallery.ui.gallery;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.MutableBoolean;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.cosmogallery.BuildConfig;
import com.example.cosmogallery.data.ApiUtils;
import com.example.cosmogallery.data.model.NasaApod;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class GalleryViewModel extends ViewModel {

    private static final int COUNT_IMAGE = 40;

    private static final String DATA = "data";

    private CompositeDisposable disposable = new CompositeDisposable();
    private GalleryAdapter.OnItemClickListener onItemClickListener;

    private MutableLiveData<Boolean> isErrorVisible = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private MutableLiveData<List<NasaApod>> photos = new MutableLiveData<>();

    private SwipeRefreshLayout.OnRefreshListener refreshListener = this::loadPhotos;

    public GalleryViewModel(GalleryAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        photos.setValue(new ArrayList<>());
        loadPhotos();
    }

    private void loadPhotos() {
        disposable.add(ApiUtils.getNasaApi().getApodCountPhotos(BuildConfig.API_KEY, COUNT_IMAGE, true)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable1 -> isLoading.postValue(true))
                .subscribeOn(Schedulers.newThread())
                .doFinally(() -> isLoading.postValue(false))
                .subscribe(nasaApods -> {
                            isErrorVisible.postValue(false);
                            photos.postValue(nasaApods);
                        },
                        throwable ->
                                isErrorVisible.postValue(true)
                ));
    }

    @Override
    protected void onCleared() {
        if (disposable != null)
            disposable.dispose();
    }

    public GalleryAdapter.OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

//    public ObservableBoolean getIsErrorVisible() {
//        return isErrorVisible;
//    }

    public MutableLiveData<List<NasaApod>> getPhotos() {
        return photos;
    }

    public MutableLiveData<Boolean> getIsErrorVisible() {
        return isErrorVisible;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }


    //    public ObservableBoolean getIsLoading() {
//        return isLoading;
//    }

    public SwipeRefreshLayout.OnRefreshListener getRefreshListener() {
        return refreshListener;
    }

}
