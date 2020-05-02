package com.example.cosmogallery.ui.photo;

public class PhotoViewModel {


    private String imageUrl;
    private String imageTitle;

    public PhotoViewModel(String imageUrl, String imageTitle) {
        this.imageUrl = imageUrl;
        this.imageTitle = imageTitle;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getImageTitle() {
        return imageTitle;
    }



}
