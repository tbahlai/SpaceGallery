package com.example.cosmogallery.ui.gallery;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cosmogallery.data.model.NasaApod;
import com.example.cosmogallery.databinding.ItemImageBinding;

public class GalleryViewHolder extends RecyclerView.ViewHolder {

    private ItemImageBinding binding;

    public GalleryViewHolder(@NonNull ItemImageBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(NasaApod photo, GalleryAdapter.OnItemClickListener listener) {
        binding.setImageItem(new ListItemImageViewModel(photo));
        binding.setOnItemClickListener(listener);
        binding.executePendingBindings();
    }
}
