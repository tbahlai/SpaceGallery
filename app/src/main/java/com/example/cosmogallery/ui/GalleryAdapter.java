package com.example.cosmogallery.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cosmogallery.R;
import com.example.cosmogallery.data.model.NasaApod;
import com.example.cosmogallery.databinding.ItemImageBinding;

import java.util.ArrayList;
import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryViewHolder> {

    private List<NasaApod> nasaApods;
    private OnItemClickListener onItemClickListener;

    //
    public GalleryAdapter(List<NasaApod> nasaApods, OnItemClickListener onItemClickListener) {
        this.nasaApods = nasaApods;
        this.onItemClickListener = onItemClickListener;
    }

//    public void setPhotos(List<NasaApod> nasaApods) {
//        this.nasaApods.clear();
//        this.nasaApods.addAll(nasaApods);
//        notifyDataSetChanged();
//    }

    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//        View view = inflater.inflate(R.layout.view_holder_image, parent, false);
        ItemImageBinding binding = ItemImageBinding.inflate(inflater, parent, false);
        return new GalleryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryViewHolder holder, int position) {
        holder.bind(nasaApods.get(position), onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return nasaApods.size();
    }

    //
    public interface OnItemClickListener {
        void onItemClick(String url, String title);
    }
}
