package com.example.cosmogallery.ui;

import android.os.Build;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cosmogallery.R;
import com.example.cosmogallery.data.model.NasaApod;
import com.example.cosmogallery.databinding.ItemImageBinding;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class GalleryViewHolder extends RecyclerView.ViewHolder {

//    private ImageView imagePlanet;
//    private TextView titlePhoto;
//    private ProgressBar progressBar;

    private ItemImageBinding binding;

    public GalleryViewHolder(@NonNull ItemImageBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
//        imagePlanet = itemView.findViewById(R.id.image_planet);
//        titlePhoto = itemView.findViewById(R.id.title_photo);
//        progressBar = itemView.findViewById(R.id.progress_bar);
    }

    public void bind(NasaApod photo, GalleryAdapter.OnItemClickListener listener) {

        binding.setImageItem(new ListItemImageViewModel(photo));
        binding.setOnItemClickListener(listener);
        binding.executePendingBindings();

//        Picasso.get().load(photo.getUrl()).into(imagePlanet, new Callback() {
//            @Override
//            public void onSuccess() {
//                progressBar.setVisibility(View.GONE);
//            }
//            @Override
//            public void onError(Exception e) {}
//        });
//
//        itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                listener.onItemClick(photo);
//            }
//        });
//        titlePhoto.setText(photo.getTitle());
    }

//    public ImageView getImagePlanet() {
//        return imagePlanet;
//    }
}
