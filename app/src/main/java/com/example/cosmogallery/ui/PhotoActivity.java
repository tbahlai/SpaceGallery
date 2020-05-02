package com.example.cosmogallery.ui;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.cosmogallery.R;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

public class PhotoActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 123;
    private PhotoView photoImage;
    private Boolean isActionBarHide = false;
    private Toolbar toolbar;

    private String imageUrl;
    private String imageTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        imageUrl = bundle.getString(GalleryActivity.PHOTO_URL);
        imageTitle = bundle.getString(GalleryActivity.PHOTO_TITLE);

        photoImage = findViewById(R.id.open_image);
        toolbar = findViewById(R.id.toolbar_photo);
        setSupportActionBar(toolbar);
        loadImage();
        actionBarShowHide();
    }

    private void loadImage() {
        Picasso.get().load(imageUrl).networkPolicy(NetworkPolicy.OFFLINE).into(photoImage);
    }

    private void actionBarShowHide() {
        photoImage.setOnClickListener(v -> {
            if (isActionBarHide) {
                toolbar.animate().translationY((-toolbar.getHeight())).setDuration(450).start();
                toolbar.setVisibility(View.GONE);
                isActionBarHide = false;
            } else {
                toolbar.animate().translationY(0).setDuration(300).start();
                toolbar.setVisibility(View.VISIBLE);
                isActionBarHide = true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.set_wallpaper:
                setImageAsWallpaper();
                break;
            case R.id.download_img:
                downloadImage();
                break;
            case R.id.share_img:
                shareImage();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setImageAsWallpaper() {
        Bitmap bitmap = ((BitmapDrawable)photoImage.getDrawable()).getBitmap();
        WallpaperManager manager = WallpaperManager.getInstance(getApplicationContext());
        try {
            manager.setBitmap(bitmap);
            Snackbar.make(photoImage, R.string.wallpaper_set, Snackbar.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, R.string.error, Toast.LENGTH_SHORT).show();
        }
    }

    private void shareImage() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Bitmap bitmap = ((BitmapDrawable)photoImage.getDrawable()).getBitmap();
            String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "", null);
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(path));
            shareIntent.setType("image/*");
            startActivity(Intent.createChooser(shareIntent, "Share Image"));
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
            }
        }
    }

    private void downloadImage() {
        if (isPermissionGranted()) {
            saveImageOnDownloads();
        } else {
            getPermissionForDownloadImage();
        }
    }

    private void saveImageOnDownloads() {
        Snackbar.make(photoImage, R.string.wait, Snackbar.LENGTH_SHORT).show();
        File file = new File(Environment.getExternalStoragePublicDirectory
                (Environment.DIRECTORY_DOWNLOADS), imageTitle);
        if (file.exists()) {
            file.delete();
        }
        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(imageUrl);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI
                | DownloadManager.Request.NETWORK_MOBILE);
        request.setAllowedOverRoaming(false);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,  imageTitle);
        downloadManager.enqueue(request);
    }

    private boolean isPermissionGranted() {
        return ((ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED);
    }

    private void getPermissionForDownloadImage() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                && ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(this)
                    .setMessage(R.string.no_permission)
                    .setPositiveButton("ALLOW", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(PhotoActivity.this,
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                            Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
                        }
                    }).show();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != REQUEST_CODE) return;
        if (grantResults.length != 2) return;
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
               shareImage();
        } else {
            new AlertDialog.Builder(this)
                    .setMessage(R.string.give_permission)
                    .setPositiveButton("OK", null)
                    .show();
        }
    }

}
