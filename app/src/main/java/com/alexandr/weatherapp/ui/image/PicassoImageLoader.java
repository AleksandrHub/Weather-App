package com.alexandr.weatherapp.ui.image;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;


public class PicassoImageLoader {

    private final String URL_HOME = "https://openweathermap.org/img/wn/";

    private final String URL_END = "@2x.png";

    public void loadInto(String url, ImageView container) {
        Picasso.get().load(URL_HOME + url + URL_END).into(container);
    }
}
