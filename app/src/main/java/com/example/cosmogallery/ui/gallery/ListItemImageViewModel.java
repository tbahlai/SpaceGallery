package com.example.cosmogallery.ui.gallery;

import com.example.cosmogallery.data.model.NasaApod;

public class ListItemImageViewModel {

    private String imageUrl;
    private String imageTitle;

    public ListItemImageViewModel(NasaApod nasaApod) {
        imageUrl = nasaApod.getUrl();
        imageTitle = nasaApod.getTitle();
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getImageTitle() {
        return imageTitle;
    }
}
