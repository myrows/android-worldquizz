package com.salesianostriana.worldquizapp.ui.maps;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.salesianostriana.worldquizapp.R;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter, RequestListener<Drawable> {

    private static final String TAG = "CustomInfoWindowAdapter";
    private LayoutInflater inflater;
    ImageView ivFlag;
    TextView tvTitle,tvLang,tvCapital,tvHabs;
    Marker currentMarker;

    public CustomInfoWindowAdapter(LayoutInflater inflater){
        this.inflater = inflater;
    }

    @Override
    public View getInfoContents(final Marker m) {
        currentMarker = m;
        View v = inflater.inflate(R.layout.infowindow_layout, null);
        String[] info = m.getTitle().split("#");

        tvTitle = v.findViewById(R.id.textViewTitle);
        tvCapital = v.findViewById(R.id.textViewName);
        tvLang = v.findViewById(R.id.textViewLang);
        tvHabs = v.findViewById(R.id.textViewHab);
        ivFlag = v.findViewById(R.id.imageViewFlag);

        Glide.with(v.getContext())
             .load(m.getSnippet())
                .addListener(this)
             .into(ivFlag);

        tvTitle.setText(info[0]);
        tvCapital.setText(info[1]);
        tvHabs.setText(info[2]+" M");
        tvLang.setText(info[3]);

        return v;
    }

    @Override
    public View getInfoWindow(Marker m) {
        return null;
    }

    @Override
    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
        return false;
    }

    @Override
    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
        if(currentMarker.isInfoWindowShown()) {
            currentMarker.hideInfoWindow();
            currentMarker.showInfoWindow();
        }
        return false;
    }
}
