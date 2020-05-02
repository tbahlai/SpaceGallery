package com.example.cosmogallery.ui;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.cosmogallery.BuildConfig;
import com.example.cosmogallery.data.ApiUtils;
import com.example.cosmogallery.data.model.NasaApod;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class GalleryViewModel {

    private static final int COUNT_IMAGE = 40;

    private CompositeDisposable disposable = new CompositeDisposable();
    private GalleryAdapter.OnItemClickListener onItemClickListener;

    private ObservableBoolean isErrorVisible = new ObservableBoolean(false);
    private ObservableArrayList<NasaApod> photos = new ObservableArrayList<>();
    private ObservableBoolean isLoading = new ObservableBoolean(true);

    private SwipeRefreshLayout.OnRefreshListener refreshListener = () -> loadPhotos();

    public GalleryViewModel(GalleryAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public GalleryAdapter.OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public ObservableBoolean getIsErrorVisible() {
        return isErrorVisible;
    }

    public ObservableArrayList<NasaApod> getPhotos() {
        return photos;
    }

    public ObservableBoolean getIsLoading() {
        return isLoading;
    }

    public SwipeRefreshLayout.OnRefreshListener getRefreshListener() {
        return refreshListener;
    }

    public void loadPhotos() {
        disposable.add(ApiUtils.getNasaApi().getApodCountPhotos(BuildConfig.API_KEY, COUNT_IMAGE, true)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable1 -> isLoading.set(true))
                .subscribeOn(Schedulers.io())
                .doFinally(() -> isLoading.set(false))
                .subscribe(nasaApods -> {
                            isErrorVisible.set(false);
                            photos.addAll(nasaApods);
                        },
                        throwable ->
                                isErrorVisible.set(true)
                ));
    }

    public void dispatchDestroy() {
        if (disposable != null)
            disposable.dispose();
    }
}
