package com.example.cosmogallery.data.model;

import com.example.cosmogallery.BuildConfig;

public class NasaPhoto {

    private String identifier;
    private String caption;
    private String image;
    private String date;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String getImageUrl() {
        //https://api.nasa.gov/EPIC/archive/enhanced/2016/12/04/png/epic_RBG_20161204003633.png?api_key=DEMO_KEY
        StringBuilder sb = new StringBuilder();
        System.out.println("sb==============================1" + sb);
//        sb.append("https://api.nasa.gov/EPIC/archive/natural/"); //работает
        sb.append("https://api.nasa.gov/EPIC/archive/enhanced/"); //работает
        System.out.println("sb==============================2" + sb);
//        sb.append("https://apod.nasa.gov/apod/archive/");
        String[] dateComponents = date.split(" ")[0].split("-");
        sb.append(dateComponents[0]).append('/');
        System.out.println("sb==============================3" + sb);
        sb.append(dateComponents[1]).append('/');
        System.out.println("sb==============================4" + sb);
        sb.append(dateComponents[2]).append("/png/");
        System.out.println("sb==============================5" + sb);
        sb.append(image).append(".png?api_key=").append(BuildConfig.API_KEY);
        return sb.toString();
    }
}
